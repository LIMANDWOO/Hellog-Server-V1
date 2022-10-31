package com.hellog.domain.like.domain.repository;

import com.hellog.domain.like.domain.entity.Like;
import com.hellog.domain.like.domain.entity.LikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, LikeId> {
}
