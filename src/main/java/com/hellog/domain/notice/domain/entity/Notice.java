package com.hellog.domain.notice.domain.entity;

import com.hellog.domain.notice.exception.NoticeForbiddenException;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.domain.type.UserRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Size(max = 255)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id")
    private User user;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

    private void checkAdmin(User user) {
        if (!user.getRole().equals(UserRole.ADMIN)) {
            throw NoticeForbiddenException.EXCEPTION;
        }
    }

    @Builder
    public Notice(String title, String content, User user) {

        checkAdmin(user);

        this.title = title;
        this.content = content;
        this.user = user;
    }
}
