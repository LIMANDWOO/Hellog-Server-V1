package com.hellog.domain.posting.service;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.comment.domain.repository.CommentRepository;
import com.hellog.domain.like.domain.repository.LikeRepository;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.posting.exception.PostingNotFoundException;
import com.hellog.domain.posting.presentation.dto.request.CreatePostingRequest;
import com.hellog.domain.posting.presentation.dto.request.UpdatePostingRequest;
import com.hellog.domain.posting.presentation.dto.response.PostingResponse;
import com.hellog.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Transactional(readOnly = true)
    public List<Posting> getTrendingPosting(int offset, int size) {
        // TODO : fetch join + pageable 함께 구현
        Pageable pageable = PageRequest.of(offset, size, Sort.by("likeCount").descending());
        return postingRepository.findAll(pageable).getContent();
    }

    @Transactional(readOnly = true)
    public PostingResponse getPostingById(long id, User user) {
        Posting posting = postingRepository.findById(id)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        List<Comment> comments = commentRepository.findAllByPosting(posting);
        boolean liked = likeRepository.existsByPostingAndUser(posting, user);
        return PostingResponse.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .content(posting.getContent())
                .thumbnailUrl(posting.getThumbnailUrl())
                .status(posting.getStatus())
                .likeCount(posting.getLikeCount())
                .user(posting.getUser())
                .summary(posting.getSummary())
                .comments(comments)
                .liked(liked)
                .build();
    }

    @Transactional(readOnly = true)
    public List<Posting> getPostingByUser(User user) {
        return postingRepository.findAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<Posting> searchPostingByTitleContaining(String title) {
        return postingRepository.findAllByTitleContains(title);
    }

    @Transactional(rollbackFor = Exception.class)
    public Posting createPosting(CreatePostingRequest request, User user) {

        Posting posting = Posting.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .summary(request.getSummary())
                .user(user)
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        return postingRepository.save(posting);
    }

    @Transactional(rollbackFor = Exception.class)
    public Posting updatePosting(UpdatePostingRequest request, User user) {

        Posting posting = postingRepository.findById(request.getId())
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);

        posting.updatePosting(
                request.getTitle(),
                request.getContent(),
                request.getSummary(),
                request.getThumbnailUrl(),
                user);
        return postingRepository.save(posting);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePosting(long postingId, User user) {
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        posting.checkCanManage(user);
        postingRepository.delete(posting);
    }
}
