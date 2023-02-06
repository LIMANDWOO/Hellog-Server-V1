package com.hellog.domain.user.presentation;

import com.hellog.domain.user.facade.UserFacade;
import com.hellog.domain.user.presentation.dto.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getStudentInfoById(@RequestParam("id") long id) {
        return userFacade.getUserInfoById(id);
    }
}
