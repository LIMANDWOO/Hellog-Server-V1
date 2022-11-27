package com.hellog.domain.like.domain.repository;

import com.hellog.domain.like.domain.entity.Like;
import com.hellog.domain.like.domain.entity.LikeId;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {

    boolean existsByPostingAndUser(Posting posting, User user);
}
