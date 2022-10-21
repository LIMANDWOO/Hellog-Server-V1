package com.hellog.domain.user.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class StudentNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new StudentNotFoundException();

    private StudentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 학생을 찾지 못했습니다.");
    }
}
