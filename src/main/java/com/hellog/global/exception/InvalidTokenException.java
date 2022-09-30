package com.hellog.global.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends CustomException {

    public static final CustomException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(HttpStatus.UNAUTHORIZED, "토큰 값이 유효하지 않습니다.");
    }
}
