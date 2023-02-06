package com.hellog.domain.auth.service;

import com.hellog.domain.auth.presentation.dto.api.DodamTokenResponse;
import com.hellog.domain.auth.presentation.dto.api.DodamUserInfoRequest;
import com.hellog.domain.auth.presentation.dto.api.GoogleUserInformationResponse;
import com.hellog.domain.auth.presentation.dto.request.DodamOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.request.GoogleOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.response.TokenRefreshResponse;
import com.hellog.domain.auth.presentation.dto.response.TokenResponse;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.StudentRepository;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.service.UserService;
import com.hellog.domain.user.service.dto.CreateGuestUserDto;
import com.hellog.domain.user.service.dto.CreateStudentDto;
import com.hellog.global.lib.jwt.JwtType;
import com.hellog.global.lib.jwt.JwtUtil;
import com.hellog.global.properties.EndPointProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final UserService userService;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private final EndPointProperties endPointProperties;

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse loginWithGoogleOAuth(GoogleOAuthLoginRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", request.getAccessToken());
        headers.add("alt", "json");

        GoogleUserInformationResponse googleResponse = restTemplate.getForObject(
                endPointProperties.getGoogle().getInfo(),
                GoogleUserInformationResponse.class,
                headers);

        User user = userRepository.findByEmail(googleResponse.getEmail())
                .orElseGet(() -> userService.createGuestUser(
                        CreateGuestUserDto.builder()
                                .name(googleResponse.getName())
                                .email(googleResponse.getEmail())
                                .profileImage(googleResponse.getPicture())
                                .build()
                ));
        user.updateUserInformation(googleResponse.getPicture());
        userRepository.save(user);

        String accessToken = jwtUtil.createToken(user, JwtType.ACCESS);
        String refreshToken = jwtUtil.createToken(user, JwtType.REFRESH);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse loginWithDodamOAuth(DodamOAuthLoginRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("code", request.getCode());
        headers.add("client_id", request.getClientId());
        headers.add("client_secret", request.getClientSecret());

        DodamTokenResponse dodamTokenResponse = restTemplate.postForObject(
                endPointProperties.getDodam().getAuth(),
                null,
                DodamTokenResponse.class,
                headers
        );

        HttpHeaders headers2 = new HttpHeaders();
        headers2.add("Authorization", "Bearer " + dodamTokenResponse.getAccessToken());
        DodamUserInfoRequest dodamUserInfoRequest = restTemplate.getForObject(
                endPointProperties.getDodam().getOpen(),
                DodamUserInfoRequest.class,
                headers2
        );

        User user = userRepository.findByEmail(dodamUserInfoRequest.getData().getEmail())
                .orElseGet(() -> userService.createGuestUser(
                        CreateGuestUserDto.builder()
                                .name(dodamUserInfoRequest.getData().getName())
                                .email(dodamUserInfoRequest.getData().getEmail())
                                .profileImage(dodamUserInfoRequest.getData().getProfileImage())
                                .build()
                ));
        user.updateUserInformation(dodamUserInfoRequest.getData().getProfileImage());
        userRepository.save(user);

        if (!studentRepository.existsByUser(user)) {
            userService.createStudent(
                    CreateStudentDto.builder()
                            .email(dodamUserInfoRequest.getData().getEmail())
                            .description(dodamUserInfoRequest.getData().getName() + "의 블로그입니다.")
                            .generation(request.getGeneration())
                            .build()
            );
        }

        String accessToken = jwtUtil.createToken(user, JwtType.ACCESS);
        String refreshToken = jwtUtil.createToken(user, JwtType.REFRESH);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional(readOnly = true)
    public TokenRefreshResponse refresh(String refreshToken) {
        String newAccessToken = jwtUtil.refresh(refreshToken);
        return new TokenRefreshResponse(newAccessToken);
    }
}
