package com.hellog.domain.auth.presentation;

import com.hellog.domain.auth.presentation.dto.request.DodamOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.request.GoogleOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.response.TokenRefreshResponse;
import com.hellog.domain.auth.presentation.dto.response.TokenResponse;
import com.hellog.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login/google")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse loginWithGoogleOAuth(@RequestBody @Valid GoogleOAuthLoginRequest request) {
        return authService.loginWithGoogleOAuth(request);
    }

    @PostMapping("/login/dauth")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponse loginWithDodamOAuth(@RequestBody @Valid DodamOAuthLoginRequest request) {
        return authService.loginWithDodamOAuth(request);
    }

    @PostMapping("/refresh")
    @ResponseStatus(HttpStatus.OK)
    public TokenRefreshResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
    }
}
