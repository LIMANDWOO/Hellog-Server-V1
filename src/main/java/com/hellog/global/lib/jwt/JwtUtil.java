package com.hellog.global.lib.jwt;

import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.repository.UserRepository;
import com.hellog.domain.user.exception.UserNotFoundException;
import com.hellog.global.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;
    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;

    public String createToken(User user, JwtType jwtType) {

        Date nowDate = new Date();
        Calendar expiredDate = Calendar.getInstance();
        expiredDate.setTime(nowDate);

        String secretKey = "";

        switch(jwtType) {
            case ACCESS:
                expiredDate.add(Calendar.DATE, 10);
                secretKey = jwtProperties.getSecret().getAccess();
            case REFRESH:
                expiredDate.add(Calendar.DATE, 20);
                secretKey = jwtProperties.getSecret().getRefresh();
        }

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secretKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        Map<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS512");

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("id", user.getId());

        JwtBuilder builder = Jwts.builder().setHeaderParams(headerMap)
                .setClaims(claimsMap)
                .setExpiration(expiredDate.getTime())
                .signWith(signatureAlgorithm, signingKey);

        return builder.compact();
    }

    public User validateToken(String token) {

        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret().getAccess())).parseClaimsJws(token).getBody();
        return userRepository.findById(claims.get("id", Long.class))
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public String refresh(String refreshToken) {

        Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwtProperties.getSecret().getRefresh())).parseClaimsJws(refreshToken).getBody();
        User user = userRepository.findById(claims.get("id", Long.class))
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);

        return createToken(user, JwtType.ACCESS);
    }

    public String extract(HttpServletRequest request, String type) {

        Enumeration<String> headers = request.getHeaders("Authorization");

        while(headers.hasMoreElements()) {
            String value = headers.nextElement();
            if(value.toLowerCase().startsWith(type.toLowerCase())) {
                return value.substring(type.length()).trim();
            }
        }

        return Strings.EMPTY;
    }
}
