package com.hellog.domain.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateGuestUserDto {

    private String name;
    private String email;
    private String profileImage;
}
