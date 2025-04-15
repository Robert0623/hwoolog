package com.hwoolog.controller;

import com.hwoolog.controller.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class PostController {

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
    public Map<String, String> posts(@RequestBody @Valid PostCreate params, BindingResult result) throws Exception {
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
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);
            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // 에러 메시지

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        log.info("params={}", params.toString());
        return Map.of();
    }
}
