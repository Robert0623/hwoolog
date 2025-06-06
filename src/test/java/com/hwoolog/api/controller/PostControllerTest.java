package com.hwoolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwoolog.api.config.HwoologMockUser;
import com.hwoolog.api.domain.Post;
import com.hwoolog.api.domain.User;
import com.hwoolog.api.repository.post.PostRepository;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.request.post.PostCreate;
import com.hwoolog.api.request.post.PostEdit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성 요청 시 title 값은 필수다.")
    // @WithMockUser(username = "aaa@aaa.com", roles = {"ADMIN"})
    @HwoologMockUser
    void test2() throws Exception {
        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                // .andExpect(status().isOk())
                .andExpect(status().isBadRequest())
                // .andExpect(jsonPath("$.title").value("타이틀을 입력해주세요."))
//                .andExpect(jsonPath("$.code").value("400"))
//                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
//                .andExpect(jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(print());
    }

    @Test
    @DisplayName("게시글 작성")
    // @WithMockUser(username = "aaa@aaa.com", roles = {"ADMIN"})
    @HwoologMockUser
    void test3() throws Exception {
        // before -> @BeforeEach

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(post("/posts")
                        .header("authorization", "hwoo")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        // then
        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        // given
        User user = User.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                // .title("foo")
                .title("123456789012345")
                .content("bar")
                .build();
        postRepository.save(post);

        // expected (when + then)
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                // .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.title").value("1234567890"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test5() throws Exception {
        // given
        User user = User.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .user(user)
                        .title("foo" + i)
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

        System.out.println("requestPosts.size() = " + requestPosts.size());
        System.out.println("requestPosts.get(0).getId() = " + requestPosts.get(0).getId());
        System.out.println("requestPosts.get(requestPosts.size() - 1).getId() = " + requestPosts.get(requestPosts.size() - 1).getId());

        // expected (when + then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                /**
                 * {id: ..., title: ...}
                 */

                /**
                 * [{id: ..., title: ...}, {id: ..., title: ....}]
                 */
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(requestPosts.get(requestPosts.size() - 1).getId()))
                .andExpect(jsonPath("$[0].title").value("foo19"))
                .andExpect(jsonPath("$[0].content").value("bar19"))
                .andDo(print());
    }

    @Test
    @DisplayName("페이지를 0으로 요청하면 첫 페이지를 가져온다.")
    void test6() throws Exception {
        // given
        User user = User.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();

        userRepository.save(user);

        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .user(user)
                        .title("foo" + i)
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);
        System.out.println("requestPosts.size() = " + requestPosts.size());
        System.out.println("requestPosts.get(0).getId() = " + requestPosts.get(0).getId());
        System.out.println("requestPosts.get(requestPosts.size() - 1).getId() = " + requestPosts.get(requestPosts.size() - 1).getId());

        // expected (when + then)
        mockMvc.perform(get("/posts?page=0&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                /**
                 * {id: ..., title: ...}
                 */

                /**
                 * [{id: ..., title: ...}, {id: ..., title: ....}]
                 */
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].id").value(requestPosts.get(requestPosts.size() - 1).getId()))
                .andExpect(jsonPath("$[0].title").value("foo19"))
                .andExpect(jsonPath("$[0].content").value("bar19"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 제목 수정")
    // @WithMockUser(username = "aaa@aaa.com", roles = {"ADMIN"})
    @HwoologMockUser
    void test7() throws Exception {
        // given
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .user(user)
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("foo2")
                .content("bar")
                .build();

        // expected (when + then)
        mockMvc.perform(patch("/posts/{postId}", post.getId()) // PATCH /posts/{postsId}
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("글 내용 삭제")
    // @WithMockUser(username = "aaa@aaa.com", roles = {"ADMIN"})
    @HwoologMockUser
    void test8() throws Exception {
        // given
        User user = userRepository.findAll().get(0);

        Post post = Post.builder()
                .user(user)
                .title("foo")
                .content("bar")
                .build();
        postRepository.save(post);

        assertEquals(1, postRepository.count());

        // expected (when + then)
        mockMvc.perform(delete("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(0, postRepository.count());
    }

    @Test
    @DisplayName("존재하지 않는 게시글 조회")
    void test9() throws Exception {
        // expected
        mockMvc.perform(get("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(print());

    }

    @Test
    @DisplayName("존재하지 않는 게시글 수정")
    // @WithMockUser(username = "aaa@aaa.com", roles = {"ADMIN"})
    @HwoologMockUser
    void test10() throws Exception {
        // given
        PostEdit postEdit = PostEdit.builder()
                .title("foo")
                .content("boo")
                .build();

        // expected
        mockMvc.perform(patch("/posts/{postId}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(postEdit)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
}