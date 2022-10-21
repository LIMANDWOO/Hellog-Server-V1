package com.hellog.domain.posting.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PostingNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new PostingNotFoundException();

    private PostingNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 포스팅을 찾을 수 없습니다.");
    }
}
