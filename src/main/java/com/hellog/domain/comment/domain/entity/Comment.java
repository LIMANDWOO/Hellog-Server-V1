package com.hellog.domain.comment.domain.entity;

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

    @EmbeddedId
    private CommentId id;

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @MapsId("posting")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_posting_id", nullable = false)
    private Posting posting;

    @Builder
    public Comment(CommentId id, User user, Posting posting) {
        this.id = id;
        this.user = user;
        this.posting = posting;
    }
}
