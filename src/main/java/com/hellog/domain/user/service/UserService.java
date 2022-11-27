package com.hellog.domain.user.service;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.StudentRepository;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.exception.StudentNotFoundException;
import com.hellog.domain.user.exception.UserNotFoundException;
import com.hellog.domain.user.presentation.dto.response.StudentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final PostingRepository postingRepository;
    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://kauth.kakao.com/oauth/authorize")
            .build();

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
    public StudentResponse getStudentByUserWithPostings(User user) {
        Student student = studentRepository.findByUser(user)
                .orElseThrow(() -> StudentNotFoundException.EXCEPTION);
        List<Posting> postings = postingRepository.findAllByStudent(student);
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .generation(student.getGeneration())
                .description(student.getDescription())
                .user(user)
                .postings(postings)
                .build();
    }
}
