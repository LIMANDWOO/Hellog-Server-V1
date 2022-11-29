package com.hellog.domain.auth.presentation.dto.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class DodamTokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String expiresIn;
}
