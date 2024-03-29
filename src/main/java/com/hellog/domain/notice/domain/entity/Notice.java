package com.hellog.domain.notice.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.notice.exception.NoticeForbiddenException;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.type.UserRole;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Notice extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    public void checkAdmin(User user) {
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw NoticeForbiddenException.EXCEPTION;
        }
    }

    public void updateNotice(String title, String content, User user) {
        checkAdmin(user);
        this.title = title;
        this.content = content;
        this.user = user;
    }

    @Builder
    public Notice(String title, String content, User user) {
        checkAdmin(user);
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
