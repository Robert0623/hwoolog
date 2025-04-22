package com.hwoolog.api.service;

import com.hwoolog.api.domain.Post;
import com.hwoolog.api.domain.PostEditor;
import com.hwoolog.api.exception.PostNotFound;
import com.hwoolog.api.repository.PostRepository;
import com.hwoolog.api.request.PostCreate;
import com.hwoolog.api.request.PostEdit;
import com.hwoolog.api.request.PostSerch;
import com.hwoolog.api.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
                .orElseThrow(PostNotFound::new);

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

    // 그링 너무 많은 경우 -> 비용이 너무 많이 든다.
    // 글이 -> 100,000,000 -> DB 글 모두 조회하는 경우 -> DB가 뻗을 수 있다.
    // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽비용 등이 많이 발생할 수 있다.

    public List<PostResponse> getList(PostSerch postSerch) {
        // web -> page 1 -> page 0
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.getList(postSerch).stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
                .map(PostResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

//        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
//
//        if (postEdit.getTitle() != null) {
//            editorBuilder.title(postEdit.getTitle());
//        }
//
//        if (postEdit.getContent() != null) {
//            editorBuilder.content(postEdit.getContent());
//        }
//
//        post.edit(editorBuilder.build());

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();
        PostEditor postEditor = editorBuilder.title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditor);
    }

    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFound::new);

        postRepository.delete(post);
    }
}
