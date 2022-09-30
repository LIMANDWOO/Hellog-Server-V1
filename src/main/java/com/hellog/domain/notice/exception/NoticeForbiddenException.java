package com.hellog.domain.notice.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoticeForbiddenException extends CustomException {

    public static final CustomException EXCEPTION = new NoticeForbiddenException();

    private NoticeForbiddenException() {
        super(HttpStatus.FORBIDDEN, "공지사항에 대한 권한이 없습니다.");
    }
}
