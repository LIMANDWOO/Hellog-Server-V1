package com.hellog.domain.posting.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class PostingForbiddenException extends CustomException {

    public static final CustomException EXCEPTION = new PostingForbiddenException();

    private PostingForbiddenException() {
        super(HttpStatus.FORBIDDEN, "해당 글에 대한 권한이 없습니다.");
    }
}
