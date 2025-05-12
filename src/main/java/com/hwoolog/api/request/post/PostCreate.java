package com.hwoolog.api.request.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private final String title;

    @NotBlank(message = "컨텐츠를 입력해주세요.")
    private final String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더의 장점
    //  - 가독성이 좋다. (값 생성에 대한 유연함)
    //  - 필요한 값만 받을 수 있다. // -> (오버로딩 가능한 조건 찾아보세요)
    //  - 객체의 불변성

    public PostCreate changeTitle(String title) {
        return PostCreate.builder()
                .title(title)
                .content(content)
                .build();
    }

    public PostCreate changeContent(String content) {
        return PostCreate.builder()
                .title(title)
                .content(content)
                .build();
    }
}
