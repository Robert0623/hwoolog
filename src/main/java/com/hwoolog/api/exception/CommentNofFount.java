package com.hwoolog.api.exception;

public class CommentNofFount extends HwoologException {

    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public CommentNofFount() {
        super(MESSAGE);
    }

    public CommentNofFount(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
