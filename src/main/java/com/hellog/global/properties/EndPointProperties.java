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
@ConfigurationProperties(prefix = "app.end-point")
public class EndPointProperties {

    @NotNull
    private final Dodam dodam;

    @NotNull
    private final Google google;

    @Getter
    @RequiredArgsConstructor
    public static final class Dodam {

        @NotBlank
        private final String auth;

        @NotBlank
        private final String open;
    }

    @Getter
    @RequiredArgsConstructor
    public static final class Google {

        @NotBlank
        private final String info;
    }
}
