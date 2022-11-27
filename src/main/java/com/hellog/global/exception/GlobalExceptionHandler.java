package com.hellog.global.exception;

import com.hellog.domain.log.service.LogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final LogService logService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        logService.writeLog(e.getStatus(), e.getClass().getName(), e.getMessage());
        return new ResponseEntity<>(
                new ErrorResponse(e.getStatus().value(), e.getMessage()),
                e.getStatus()
        );
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleAllExceptions(Exception e) {
//        logService.writeLog(HttpStatus.INTERNAL_SERVER_ERROR, e.getClass().getName(), e.getMessage());
//        return new ResponseEntity<>(
//                new ErrorResponse(500, e.getMessage()),
//                500
//        );
//    }
}
