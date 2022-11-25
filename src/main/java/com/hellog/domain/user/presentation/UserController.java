package com.hellog.domain.user.presentation;

import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.service.UserService;
import com.hellog.global.annotation.AuthenticationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @AuthenticationCheck
    @GetMapping("/myinfo")
    @ResponseStatus(HttpStatus.OK)
    public Student getMyInfo(@RequestAttribute User user) {
        return userService.getStudentByUser(user);
    }
}
