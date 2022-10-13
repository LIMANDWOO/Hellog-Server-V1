package com.hellog.domain.like.domain.entity;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "like")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Like extends BaseTime {

    @EmbeddedId
    private LikeId id;

    @MapsId("user")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_user_id", nullable = false)
    private User user;

    @MapsId("posting")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_posting_id", nullable = false)
    private Posting posting;

    @Builder
    public Like(LikeId id, User user, Posting posting) {
        this.id = id;
        this.user = user;
        this.posting = posting;
    }
}
