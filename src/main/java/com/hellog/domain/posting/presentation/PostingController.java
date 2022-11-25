package com.hellog.domain.posting.presentation;

import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.comment.presentation.dto.request.CreateCommentRequest;
import com.hellog.domain.comment.service.CommentService;
import com.hellog.domain.like.service.LikeService;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.presentation.dto.request.CreatePostingRequest;
import com.hellog.domain.posting.presentation.dto.request.UpdatePostingRequest;
import com.hellog.domain.posting.presentation.dto.response.PostingResponse;
import com.hellog.domain.posting.service.PostingService;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.annotation.AuthenticationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/posting")
@RequiredArgsConstructor
public class PostingController {

    private final PostingService postingService;
    private final LikeService likeService;
    private final CommentService commentService;

    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    public List<Posting> getTrendingPostings() {
        return postingService.getTrendingPosting();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PostingResponse getPostingById(@PathVariable("id") long postingId) {
        return postingService.getPostingById(postingId);
    }

    @AuthenticationCheck
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Posting createPosting(
            @RequestBody @Valid CreatePostingRequest request,
            @RequestAttribute User user
    ) {
        return postingService.createPosting(request, user);
    }

    @AuthenticationCheck
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Posting updatePosting(
            @RequestBody @Valid UpdatePostingRequest request,
            @RequestAttribute User user
    ) {
        return postingService.updatePosting(request, user);
    }

    @AuthenticationCheck
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePosting(
            @PathVariable("id") long postingId,
            @RequestAttribute User user
    ) {
        postingService.deletePosting(postingId, user);
    }

    @AuthenticationCheck
    @PostMapping("/like/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPostingLike(
            @PathVariable("id") long postingId,
            @RequestAttribute User user
    ) {
        likeService.createLike(postingId, user);
    }

    @AuthenticationCheck
    @DeleteMapping("/like/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostingLike(
            @PathVariable("id") long postingId,
            @RequestAttribute User user
    ) {
        likeService.deleteLike(postingId, user);
    }

    @AuthenticationCheck
    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createPostingComment(
            @RequestBody @Valid CreateCommentRequest request,
            @RequestAttribute User user
    ) {
        return commentService.createComment(request, user);
    }

    @AuthenticationCheck
    @DeleteMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePostingComment(
            @PathVariable("id") long commentId,
            @RequestAttribute User user
    ) {
        commentService.deleteComment(commentId, user);
    }
}
