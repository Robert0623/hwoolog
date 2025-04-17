package com.hwoolog.api.service;

import com.hwoolog.api.domain.Post;
import com.hwoolog.api.repository.PostRepository;
import com.hwoolog.api.request.PostCreate;
import com.hwoolog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // postCreate(Dto) -> Entity
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);

        // return post.getId();
    }

    public PostResponse get(Long id) {
        Post post =  postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        // 응답을 위한 클래스를 분리 (서비스 정책 적용(title 10글자 이하))
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        /**
         * PostController -> WebPostService (response 호출 담당) -> Repository
         *                   PostService (다른 서비스와 통신)
         */
    }

}
