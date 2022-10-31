package com.hellog.domain.like.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class LikeNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new LikeNotFoundException();

    private LikeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 좋아요를 찾을 수 없습니다.");
    }
}
