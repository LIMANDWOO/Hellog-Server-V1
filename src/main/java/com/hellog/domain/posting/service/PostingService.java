package com.hellog.domain.posting.service;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.comment.domain.repository.CommentRepository;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.posting.exception.PostingNotFoundException;
import com.hellog.domain.posting.presentation.dto.request.CreatePostingRequest;
import com.hellog.domain.posting.presentation.dto.request.UpdatePostingRequest;
import com.hellog.domain.posting.presentation.dto.response.PostingResponse;
import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingService {

    private final PostingRepository postingRepository;
    private final UserService userService;
    private final CommentRepository commentRepository;

    @Transactional(readOnly = true)
    public List<Posting> getTrendingPosting() {
        return postingRepository.findAllByOrderByLikeCountDesc();
    }

    @Transactional(readOnly = true)
    public PostingResponse getPostingById(long id) {
        Posting posting = postingRepository.findById(id)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        List<Comment> comments = commentRepository.findAllByPosting(posting);
        return PostingResponse.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .content(posting.getContent())
                .thumbnailUrl(posting.getThumbnailUrl())
                .status(posting.getStatus())
                .likeCount(posting.getLikeCount())
                .student(posting.getStudent())
                .summary(posting.getSummary())
                .comments(comments)
                .build();
    }

    @Transactional(readOnly = true)
    public List<Posting> searchPostingByTitleContaining(String title) {
        return postingRepository.findAllByTitleContains(title);
    }

    @Transactional(rollbackFor = Exception.class)
    public Posting createPosting(CreatePostingRequest request, User user) {

        Student student = userService.getStudentByUser(user);
        Posting posting = Posting.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .summary(request.getSummary())
                .student(student)
                .thumbnailUrl(request.getThumbnailUrl())
                .build();

        return postingRepository.save(posting);
    }

    @Transactional(rollbackFor = Exception.class)
    public Posting updatePosting(UpdatePostingRequest request, User user) {

        Student student = userService.getStudentByUser(user);
        Posting posting = postingRepository.findById(request.getId())
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);

        posting.updatePosting(
                request.getTitle(),
                request.getContent(),
                request.getSummary(),
                request.getThumbnailUrl(),
                student);
        return postingRepository.save(posting);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deletePosting(long postingId, User user) {
        Student student = userService.getStudentByUser(user);
        Posting posting = postingRepository.findById(postingId)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        posting.checkCanManage(student);
        postingRepository.delete(posting);
    }
}
