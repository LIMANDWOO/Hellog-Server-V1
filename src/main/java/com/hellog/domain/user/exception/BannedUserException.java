package com.hellog.domain.user.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class BannedUserException extends CustomException {

    public static final BannedUserException EXCEPTION = new BannedUserException();

    private BannedUserException() {
        super(HttpStatus.FORBIDDEN, "사용 정지된 유저입니다.");
    }
}
