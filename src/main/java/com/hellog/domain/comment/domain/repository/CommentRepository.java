package com.hellog.domain.comment.domain.repository;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.posting.domain.entity.Posting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<Comment> findAllByPosting(Posting posting);
}
