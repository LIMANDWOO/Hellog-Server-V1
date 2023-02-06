package com.hellog.domain.user.facade;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.service.PostingService;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.presentation.dto.response.UserResponse;
import com.hellog.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final PostingService postingService;

    public UserResponse getUserInfoById(long id) {
        User user = userService.getUserById(id);
        List<Posting> postings = postingService.getPostingByUser(user);
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .profileImage(user.getProfileImage())
                .description(user.getDescription())
                .authType(user.getAuthType())
                .postings(postings)
                .build();
    }

}
