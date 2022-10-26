package com.hellog.domain.notice.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class NoticeNotFoundException extends CustomException {

    public static final CustomException EXCEPTION = new NoticeNotFoundException();

    private NoticeNotFoundException() {
        super(HttpStatus.NOT_FOUND, "해당 공지를 찾지 못했습니다.");
    }
}
