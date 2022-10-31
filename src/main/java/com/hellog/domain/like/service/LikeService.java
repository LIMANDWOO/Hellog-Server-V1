package com.hellog.domain.like.service;

import com.hellog.domain.like.domain.entity.Like;
import com.hellog.domain.like.domain.entity.LikeId;
import com.hellog.domain.like.domain.repository.LikeRepository;
import com.hellog.domain.like.exception.LikeNotFoundException;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.posting.exception.PostingNotFoundException;
import com.hellog.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostingRepository postingRepository;
    private final LikeRepository likeRepository;

    @Transactional(rollbackFor = Exception.class)
    public void createLike(long postingId, User user) {

        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        LikeId likeId = new LikeId(postingId, user.getId());
        Like like = likeRepository.findById(likeId)
                .orElseGet(() -> Like.builder()
                        .id(likeId)
                        .user(user)
                        .posting(posting)
                        .build());
        likeRepository.save(like);

        posting.increaseLikeCount();
        postingRepository.save(posting);
    }

    public void deleteLike(long postingId, User user) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        LikeId likeId = new LikeId(postingId, user.getId());
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> LikeNotFoundException.EXCEPTION);
        likeRepository.delete(like);

        posting.decreaseLikeCount();
        postingRepository.save(posting);
    }
}
