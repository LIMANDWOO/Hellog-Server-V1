package com.hellog.domain.user.service;

import com.hellog.domain.user.domain.repository.StudentRepository;
import com.hellog.domain.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://kauth.kakao.com/oauth/authorize")
            .build();
}
