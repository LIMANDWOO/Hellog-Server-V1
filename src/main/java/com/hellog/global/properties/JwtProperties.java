package com.hellog.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {

    @NotBlank
    private final String issuer;

    @NotNull
    private final Secret secret;

    @Getter
    @RequiredArgsConstructor
    public static final class Secret {

        @NotBlank
        private final String access;

        @NotBlank
        private final String refresh;
    }
}
