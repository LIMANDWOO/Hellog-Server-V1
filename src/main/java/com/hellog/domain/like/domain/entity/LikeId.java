package com.hellog.domain.like.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class LikeId implements Serializable {

    private long user;

    private long posting;

    public LikeId(long postingId, long userId) {
        posting = postingId;
        user = userId;
    }
}
