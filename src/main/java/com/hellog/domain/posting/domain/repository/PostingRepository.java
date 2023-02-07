package com.hellog.domain.posting.domain.repository;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {

    @Query("select p from Posting p join fetch p.user u")
    List<Posting> findAllByUser(User user);

    @Query("select p from Posting p join fetch p.user u where p.id=:id")
    Optional<Posting> findById(long id);

    List<Posting> findAllByTitleContains(String title);
}
