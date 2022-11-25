package com.hellog.global.security.auth;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.global.lib.encrypt.Encrypt;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final Encrypt encrypt;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);

        String provider = request.getClientRegistration().getRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = encrypt.encode(uuid);

        String email = oAuth2User.getAttribute("email");
        String profileImage = oAuth2User.getAttribute("picture");
        UserRole role = UserRole.GUEST;

        User user = userRepository.findByEmail(email)
                .orElseGet(() -> User.builder()
                        .email(email)
                        .profileImage(profileImage)
                        .role(role)
                        .password(password)
                        .build());

        return new OAuth2Details(user, oAuth2User.getAttributes());
    }
}
