package com.hwoolog.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hwoolog.api.domain.Comment;
import com.hwoolog.api.domain.Post;
import com.hwoolog.api.domain.User;
import com.hwoolog.api.repository.UserRepository;
import com.hwoolog.api.repository.comment.CommentRepository;
import com.hwoolog.api.repository.post.PostRepository;
import com.hwoolog.api.request.comment.CommentCreate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// @WebMvcTest
@AutoConfigureMockMvc
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    void clean() {
        postRepository.deleteAll();
        userRepository.deleteAll();
        commentRepository.deleteAll();
    }

    @Test
    @DisplayName("댓글 작성")
    void test1() throws Exception {
        // given
        User user = User.builder()
                .name("hwoo")
                .email("aaa@aaa.com")
                .password("1234")
                .build();
        userRepository.save(user);

        Post post = Post.builder()
                .user(user)
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);
        Long postId = post.getId();

        CommentCreate request = CommentCreate.builder()
                .author("hwoo2")
                .password("123456")
                .content("0123456789")
                .build();

        String json = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/posts/{postId}/comments", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(1L, commentRepository.count());

        Comment savedComment = commentRepository.findAll().get(0);
        assertEquals("hwoo2", savedComment.getAuthor());
        assertNotEquals("123456", savedComment.getPassword());
        assertTrue(passwordEncoder.matches("123456", savedComment.getPassword()));
        assertEquals("0123456789", savedComment.getContent());
    }
}