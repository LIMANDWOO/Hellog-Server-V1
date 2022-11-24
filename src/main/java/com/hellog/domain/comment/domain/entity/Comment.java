package com.hellog.domain.comment.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "tbl_comment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_posting_id", nullable = false)
    @JsonIgnore
    private Posting posting;

    @Column(nullable = false)
    private String content;

    @Builder
    public Comment(User user, Posting posting, String content) {
        this.user = user;
        this.posting = posting;
        this.content = content;
    }
}
