package com.hellog.domain.user.presentation;

import com.hellog.domain.user.facade.UserFacade;
import com.hellog.domain.user.presentation.dto.response.UserResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @ApiOperation("유저 id를 통해 유저의 정보 및 유저가 작성한 게시물을 조회합니다")
    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public UserResponse getUserInfoById(@RequestParam("id") long id) {
        return userFacade.getUserInfoById(id);
    }
}
