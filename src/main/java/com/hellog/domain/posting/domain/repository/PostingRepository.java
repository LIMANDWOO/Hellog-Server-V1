package com.hellog.domain.posting.domain.repository;

import com.hellog.domain.posting.domain.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findAllByOrderByLikeCountDesc();
    List<Posting> findAllByTitleContains(String title);
}
