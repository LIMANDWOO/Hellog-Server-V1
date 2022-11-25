package com.hellog.domain.auth.service;

import com.hellog.domain.auth.presentation.dto.api.GoogleUserInformationResponse;
import com.hellog.domain.auth.presentation.dto.request.DodamOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.request.GoogleOAuthLoginRequest;
import com.hellog.domain.auth.presentation.dto.response.TokenRefreshResponse;
import com.hellog.domain.auth.presentation.dto.response.TokenResponse;
import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.StudentRepository;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.global.lib.jwt.JwtType;
import com.hellog.global.lib.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;
    private static final String GOOGLE_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse loginWithGoogleOAuth(GoogleOAuthLoginRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_token", request.getAccessToken());
        headers.add("alt", "json");

        GoogleUserInformationResponse response = restTemplate.getForObject(
                GOOGLE_INFO_URL,
                GoogleUserInformationResponse.class,
                headers);

        Optional<User> user = userRepository.findByEmail(response.getEmail());

        if (user.isEmpty()) {
            User newUser = User.builder()
                    .email(response.getEmail())
                    .profileImage(response.getPicture())
                    .role(UserRole.GUEST)
                    .build();
            userRepository.save(newUser);

            // TODO : 테스트 끝나면 고치기
            Student newStudent = Student.builder()
                    .name("임동현")
                    .generation(6)
                    .description("혁신적인 FE를 선호합니다.")
                    .user(newUser)
                    .build();
            studentRepository.save(newStudent);
        }

        String accessToken = jwtUtil.createToken(user.get(), JwtType.ACCESS);
        String refreshToken = jwtUtil.createToken(user.get(), JwtType.REFRESH);

        return new TokenResponse(accessToken, refreshToken);
    }

    @Transactional(rollbackFor = Exception.class)
    public TokenResponse loginWithDodamOAuth(DodamOAuthLoginRequest request) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("code", request.getCode());

        // TODO : DAuth 요청 및 User, Student 생성

        return null;
    }

    @Transactional(readOnly = true)
    public TokenRefreshResponse refresh(String refreshToken) {
        String newAccessToken = jwtUtil.refresh(refreshToken);
        return new TokenRefreshResponse(newAccessToken);
    }
}
