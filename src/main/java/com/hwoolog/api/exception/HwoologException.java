package com.hwoolog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class HwoologException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    public HwoologException(String message) {
        super(message);
    }

    public HwoologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
