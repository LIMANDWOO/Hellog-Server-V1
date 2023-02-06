package com.hellog.domain.posting.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.posting.exception.PostingNotOwnerException;
import com.hellog.domain.posting.domain.type.PostingStatus;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_posting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Posting extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Size(max = 500)
    private String thumbnailUrl;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PostingStatus status;

    @NotNull
    private long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @NotNull
    @Size(max = 255)
    private String summary;

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void checkCanManage(User user) {
        if (this.user.getId() != user.getId()) {
            throw PostingNotOwnerException.EXCEPTION;
        }
    }

    public void updatePosting(String title, String content, String summary, String thumbnailUrl, User user) {
        checkCanManage(user);
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Builder
    public Posting(String title, String content, String summary, User user, String thumbnailUrl) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.user = user;
        this.thumbnailUrl = thumbnailUrl;
        this.status = PostingStatus.ACTIVE;
        this.likeCount = 0;
    }
}
