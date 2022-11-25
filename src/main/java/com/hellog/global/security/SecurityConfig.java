package com.hellog.global.security;

import com.hellog.global.security.auth.OAuth2DetailsService;
import com.hellog.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsUtils;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2DetailsService oAuth2DetailsService;

    private static final String USER = "USER";
    private static final String ADMIN_ROOT = "ROOT";
    private static final String ADMIN_CONFIRM_APPLICATION = "CONFIRM_APPLICATION";

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/swagger-ui/**")
                .antMatchers("/swagger-resources/**")
                .antMatchers("/v2/api-docs/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                .authorizeRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers( "/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/posting/**").permitAll()
                .antMatchers(HttpMethod.GET, "/notice/**").permitAll()
                .anyRequest().authenticated()
                .and().apply(new FilterConfig(jwtTokenProvider))
                .and()
                .oauth2Login().userInfoEndpoint().userService(oAuth2DetailsService);
    }
}