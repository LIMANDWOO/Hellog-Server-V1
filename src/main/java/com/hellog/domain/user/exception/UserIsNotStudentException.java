package com.hellog.domain.user.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class UserIsNotStudentException extends CustomException {

    public static final CustomException EXCEPTION = new UserIsNotStudentException();

    private UserIsNotStudentException() {
        super(HttpStatus.FORBIDDEN, "해당 유저는 학생이 아닙니다.");
    }
}
