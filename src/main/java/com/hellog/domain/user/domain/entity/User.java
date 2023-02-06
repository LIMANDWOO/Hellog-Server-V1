package com.hellog.domain.user.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.user.domain.type.AuthType;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.domain.user.domain.type.UserStatus;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(max = 45)
    private String name;

    @Column(nullable = false, unique = true)
    @Size(max = 255)
    private String email;

    @Column(nullable = false)
    @Size(max = 255)
    private String description;

    @Column(nullable = false, length = 500)
    @Size(max = 500)
    private String profileImage;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    public void updateUserInformation(String name, String description, String profileImage) {
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
    }

    @Builder
    public User(String name, String email, String description, String profileImage, UserRole role, AuthType authType) {
        this.name = name;
        this.email = email;
        this.description = description;
        this.profileImage = profileImage;
        this.role = role;
        this.status = UserStatus.ACTIVE;
        this.authType = authType;
    }

}
