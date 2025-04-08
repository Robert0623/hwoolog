package com.hwoolog.controller;

import com.hwoolog.controller.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public String posts(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }
}
