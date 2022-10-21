package com.hellog.domain.log.service;

import com.hellog.domain.log.domain.entity.Log;
import com.hellog.domain.log.domain.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public void writeLog(HttpStatus status, String errorName, String message) {
        Log log = Log.builder()
                .status(status)
                .errorName(errorName)
                .message(message)
                .build();
        logRepository.save(log);
    }
}
