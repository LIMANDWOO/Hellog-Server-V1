package com.hellog.domain.user.presentation.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateMyInfoRequest {

    @NotBlank(message = "profileImage must not be blank.")
    private String profileImage;
}
