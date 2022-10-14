package com.hellog.domain.user.domain.entity;

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
public class User extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(max = 255)
    private String profileImage;

    @Column(nullable = false)
    @Size(max = 75)
    private String email;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status;

    @Builder
    public User(String email, String profileImage, UserRole role) {
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
        this.status = UserStatus.ACTIVE;
    }

}
