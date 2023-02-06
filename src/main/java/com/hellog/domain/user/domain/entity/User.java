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
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@DynamicInsert
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 45)
    private String name;

    @NotNull
    @Column(unique = true)
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    @Size(max = 500)
    private String profileImage;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private UserStatus status;

    @NotNull
    @Enumerated(value = EnumType.STRING)
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
        this.authType = authType;
    }

}
