package com.hellog.global.exception.global;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class ExpiredTokenException extends CustomException {

    public static final CustomException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다.");
    }
}
