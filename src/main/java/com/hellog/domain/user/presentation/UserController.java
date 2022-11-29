package com.hellog.domain.user.presentation;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.facade.UserFacade;
import com.hellog.domain.user.presentation.dto.response.StudentResponse;
import com.hellog.global.annotation.AuthenticationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/student/info")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getStudentInfoById(@RequestParam("id") long id) {
        return null;
    }

    @AuthenticationCheck
    @GetMapping("/student/myinfo")
    @ResponseStatus(HttpStatus.OK)
    public StudentResponse getMyInfo(@RequestAttribute User user) {
        return userFacade.getMyInfo(user);
    }
}
