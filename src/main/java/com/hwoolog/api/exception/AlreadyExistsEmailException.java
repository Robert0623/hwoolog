package com.hwoolog.api.exception;

import lombok.Getter;

/**
 * status -> 400
 */
@Getter
public class AlreadyExistsEmailException extends HwoologException {

    private static final String MESSAGE = "이미 가입된 이메일입니다.";

    public AlreadyExistsEmailException() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
