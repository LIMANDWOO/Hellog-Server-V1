package com.hellog.domain.user.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StudentGeneratedInvalidException extends CustomException {

    public static final CustomException EXCEPTION = new StudentGeneratedInvalidException();

    private StudentGeneratedInvalidException() {
        super(HttpStatus.BAD_REQUEST, "기수 값이 유효하지 않습니다.");
    }
}
