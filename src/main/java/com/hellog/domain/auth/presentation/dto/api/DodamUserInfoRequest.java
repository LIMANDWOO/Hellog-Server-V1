package com.hellog.domain.auth.presentation.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DodamUserInfoRequest {

    private int status;
    private String message;
    private Data data;

    @Getter
    @NoArgsConstructor
    public static class Data {
        private int grade;
        private int room;
        private int number;
        private String name;
        private String profileImage;
        private String email;
    }
}
