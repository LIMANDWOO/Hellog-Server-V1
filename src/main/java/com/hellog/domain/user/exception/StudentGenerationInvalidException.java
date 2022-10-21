package com.hellog.domain.user.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StudentGenerationInvalidException extends CustomException {

    public static final CustomException EXCEPTION = new StudentGenerationInvalidException();

    private StudentGenerationInvalidException() {
        super(HttpStatus.BAD_REQUEST, "기수 값이 유효하지 않습니다.");
    }
}
