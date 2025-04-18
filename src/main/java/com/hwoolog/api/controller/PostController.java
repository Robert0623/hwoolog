package com.hwoolog.api.controller;

import com.hwoolog.api.request.PostCreate;
import com.hwoolog.api.response.PostResponse;
import com.hwoolog.api.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    // SSR -> jsp, thymeleaf, mustache, freemarker
            // -> html rendering
    // SPA ->
    //   vue, nuxt
    //   react, next
        // -> javascript + <-> API (JSON)


    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글 등록
    // POST Method
    @PostMapping("/posts")
    public void posts(@RequestBody @Valid PostCreate request
                      // , BindingResult result
    ) throws Exception {
        // 데이터를 검증하는 이유

        // 1. client 개발자가 깜박할 수 있다. 시룻로 값을 안보낼 수 있다.
        // 2. client bug로 값이 누락될 수 있다.
        // 3. 외부에서 값을 임의로 조작해서 보낼 수 있다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        // 5. 서버 개발자의 편안함을 위해서

//        String title = params.getTitle();
//        if (title == null || title.equals("")) { // @Valid, @NotBlank로 대체
            // 1. 빡세다. (노가다)
            // 2. 개발팁 -> 무언가 3번 이상 반복작업을 할 때 내가 뭔가 잘못하고 있는건 아닌지 의심한다.
            // 3. 누락가능성
            // 4. 생각보다 검증해야할 것이 많다. (꼼꼼하지 않을 수 있다.)
            // 5. 뭔가 개발자스럽지 않다. -> 간지 X

            // {"title": ""}
            // {"title": "          "}
            // {"title": "............수십억 글자"}
//            throw new Exception("타이틀값이 없어요!");
//        }

        // {"title": "타이틀 값이 없습니다"}
        // 1. 매번 매서드마다 값을 검증해야한다.
        //      > 개발자가 까먹을 수 있다.
        //      > 검증 부분에서 버그가 발생할 여지가 높다.
        //      > 지겹다. (간지가 안난다.)
        // 2. 응답값에 HashMap -> 응답 클래스를 만들어주는 것이 좋다.
        // 3. 여러개의 예외처리 힘듬
        // 4. 세 번 이상의 반복적인 작업은 피해야한다.
            // - 코드 && 개발에 관한 모든 것
                // -> 자동화 고려
//        if (result.hasErrors()) {
//            List<FieldError> fieldErrors = result.getFieldErrors();
//            FieldError firstFieldError = fieldErrors.get(0);
//            String fieldName = firstFieldError.getField(); // title
//            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메시지
//
//            FieldError secondFieldError = fieldErrors.get(1);
//            String secondFieldName = secondFieldError.getField();
//            String secondErrorMessage = secondFieldError.getDefaultMessage();
//
//            Map<String, String> error = new HashMap<>();
//            error.put(fieldName, errorMessage);
//            error.put(secondFieldName, secondErrorMessage);
//            return error;
//        }
        log.info("request={}", request.toString());
        // Case1. 저장한 데이터 Entity -> response로 응답하기
        // Case2. 저장한 데이터의 primary_id -> response로 응답하기
        //          Client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받음
        // Case3. 응답 필요 없음 -> Client에서 모든 글 데이터 context를 잘 관리함
        // Bad Case: 서버에서 -> 반드시 이렇게 할겁니다! fix -> 안좋다!
        //              -> 서버에서 차라리 유연하게 대응하는게 좋습니다 -> 코드를 잘 짜야겠죠!
        //              -> 한 번에 일괄적으로 잘 처리되는 케이스가 없습니다 -> 잘 관리하는 형태가 중요합니다.
        // return postService.write(request);
        // Long postId = postService.write(request);
        // return Map.of("postId", postId);
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
    public List<PostResponse> getList() {
        return postService.getList(1);
    }

}
