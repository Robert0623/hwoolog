package com.hwoolog.api.controller;

import com.hwoolog.api.request.PostCreate;
import com.hwoolog.api.request.PostEdit;
import com.hwoolog.api.request.PostSerch;
import com.hwoolog.api.response.PostResponse;
import com.hwoolog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/posts")
    public void posts(@RequestBody @Valid PostCreate request
                      // , BindingResult result
    ) throws Exception {
        postService.write(request);
    }

    /**
     * /posts -> 글 전체 조회 (검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */
    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable Long postId) {
        // Request 클래스 -> 요청과 validation 정책
        // Response 클래스 -> 서비스 정책
        return postService.get(postId);
    }

    /**
     * 조회 API
     * 여러개의 글을 조회
     * /posts
     */
    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSerch postSerch) {
        return postService.getList(postSerch);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }
}
