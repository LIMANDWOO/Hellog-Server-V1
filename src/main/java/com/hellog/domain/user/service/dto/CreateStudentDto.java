package com.hellog.domain.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateStudentDto {

    private String email;
    private int generation;
    private String description;
}
