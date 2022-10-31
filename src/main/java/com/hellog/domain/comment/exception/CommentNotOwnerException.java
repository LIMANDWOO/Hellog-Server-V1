package com.hellog.domain.comment.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentNotOwnerException extends CustomException {

    public static final CustomException EXCEPTION = new CommentNotOwnerException();

    private CommentNotOwnerException() {
        super(HttpStatus.FORBIDDEN, "해당 댓글에 대한 권한이 없습니다.");
    }
}
