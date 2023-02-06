package com.hellog.domain.user.service;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.domain.user.exception.UserNotFoundException;
import com.hellog.domain.user.service.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public User getUserById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Transactional(rollbackFor = Exception.class)
    public User createUser(CreateUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .description(dto.getDescription())
                .profileImage(dto.getProfileImage())
                .role(UserRole.USER)
                .build();
        return userRepository.save(user);
    }
}
