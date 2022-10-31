package com.hellog.domain.comment.service;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.comment.domain.repository.CommentRepository;
import com.hellog.domain.comment.exception.CommentNotFoundException;
import com.hellog.domain.comment.exception.CommentNotOwnerException;
import com.hellog.domain.comment.presentation.dto.request.CreateCommentRequest;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.posting.exception.PostingNotFoundException;
import com.hellog.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostingRepository postingRepository;

    public Comment createComment(CreateCommentRequest request, User user) {
        Posting posting = postingRepository.findById(request.getPostingId())
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
        Comment comment = Comment.builder()
                .user(user)
                .posting(posting)
                .content(request.getContent())
                .build();
        return commentRepository.save(comment);
    }

    public void deleteComment(long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> CommentNotFoundException.EXCEPTION);
        if(comment.getUser().getId() != user.getId()) {
            throw CommentNotOwnerException.EXCEPTION;
        }
        commentRepository.delete(comment);
    }
}
