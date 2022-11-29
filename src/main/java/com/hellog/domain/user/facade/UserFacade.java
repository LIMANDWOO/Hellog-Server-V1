package com.hellog.domain.user.facade;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.service.PostingService;
import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.presentation.dto.response.StudentResponse;
import com.hellog.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PostingService postingService;

    public StudentResponse getMyInfo(User user) {
        Student student = userService.getStudentByUser(user);
        List<Posting> postings = postingService.getPostingByStudent(student);
        return StudentResponse.builder()
                .id(student.getId())
                .generation(student.getGeneration())
                .description(student.getDescription())
                .user(user)
                .postings(postings)
                .build();
    }

    public StudentResponse getMyInfo(long id) {
        Student student = userService.getStudentById(id);
        List<Posting> postings = postingService.getPostingByStudent(student);
        return StudentResponse.builder()
                .id(student.getId())
                .generation(student.getGeneration())
                .description(student.getDescription())
                .user(student.getUser())
                .postings(postings)
                .build();
    }

}
