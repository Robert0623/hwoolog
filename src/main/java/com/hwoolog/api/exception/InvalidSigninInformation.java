package com.hwoolog.api.exception;

import lombok.Getter;

/**
 * status -> 400
 */
@Getter
public class InvalidSigninInformation extends HwoologException {

    private static final String MESSAGE = "아이디/비밀번호가 올바르지 않습니다.";

    public InvalidSigninInformation() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
