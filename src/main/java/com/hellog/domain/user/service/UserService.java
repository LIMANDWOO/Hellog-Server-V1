package com.hellog.domain.user.service;

import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.StudentRepository;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.domain.user.exception.StudentNotFoundException;
import com.hellog.domain.user.exception.UserNotFoundException;
import com.hellog.domain.user.service.dto.CreateGuestUserDto;
import com.hellog.domain.user.service.dto.CreateStudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public Student getStudentByUser(User user) {
        return studentRepository.findByUser(user)
                .orElseThrow(() -> StudentNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public Student getStudentById(long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> StudentNotFoundException.EXCEPTION);
    }

    @Transactional(rollbackFor = Exception.class)
    public User createGuestUser(CreateGuestUserDto dto) {
        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .profileImage(dto.getProfileImage())
                .role(UserRole.GUEST)
                .build();
        return userRepository.save(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public Student createStudent(CreateStudentDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
        Student student = Student.builder()
                .userId(user.getId())
                .generation(dto.getGeneration())
                .description(dto.getDescription())
                .user(user)
                .build();
        return studentRepository.save(student);
    }
}
