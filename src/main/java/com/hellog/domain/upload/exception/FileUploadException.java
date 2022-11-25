package com.hellog.domain.upload.exception;

import com.hellog.global.exception.CustomException;
import org.springframework.http.HttpStatus;

public class FileUploadException extends CustomException {

    public static final CustomException EXCEPTION = new FileUploadException();

    private FileUploadException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드 중 오류가 발생했습니다.");
    }
}
