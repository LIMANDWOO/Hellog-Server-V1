package com.hellog.domain.user.service.dto;

import com.hellog.domain.user.domain.type.AuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateUserDto {

    private String name;
    private String email;
    private String description;
    private String profileImage;
    private AuthType authType;
}
