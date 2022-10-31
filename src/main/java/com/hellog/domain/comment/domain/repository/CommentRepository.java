package com.hellog.domain.comment.domain.repository;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.comment.domain.entity.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
