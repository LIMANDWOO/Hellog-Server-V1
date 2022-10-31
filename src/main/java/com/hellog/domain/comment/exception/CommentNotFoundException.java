package com.hellog.domain.comment.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new CommentNotFoundException();

    private CommentNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 댓글을 찾을 수 없습니다.");
    }
}
