package com.hellog.domain.user.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    STUDENT("ROLE_STUDENT"),
    GUEST("ROLE_GUEST"),
    ADMIN("ROLE_ADMIN");

    private final String key;
}
