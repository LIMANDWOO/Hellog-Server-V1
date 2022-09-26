package com.hellog.domain.user.service;

import com.hellog.domain.user.repository.StudentRepository;
import com.hellog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
}
