package com.hellog.global.auth.interceptor;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.service.UserService;
import com.hellog.global.auth.annotation.AuthenticationCheck;
import com.hellog.global.exception.global.InvalidTokenException;
import com.hellog.global.lib.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        AuthenticationCheck authenticationCheck = handlerMethod.getMethodAnnotation(AuthenticationCheck.class);

        if (authenticationCheck == null) {
            return true;
        }

        String token = jwtUtil.extract(request, "Bearer");

        if (token == null || token.length() == 0) {
            throw InvalidTokenException.EXCEPTION;
        }

        User user = jwtUtil.validateToken(token);
        request.setAttribute("user", user);
        return true;
    }
}
