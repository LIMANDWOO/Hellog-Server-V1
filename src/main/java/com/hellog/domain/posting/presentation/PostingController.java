package com.hellog.domain.posting.presentation;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.presentation.dto.request.CreatePostingRequest;
import com.hellog.domain.posting.presentation.dto.request.UpdatePostingRequest;
import com.hellog.domain.posting.service.PostingService;
import com.hellog.domain.user.domain.entity.User;
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

    @GetMapping("/trending")
    @ResponseStatus(HttpStatus.OK)
    public List<Posting> getTrendingPostings() {
        return postingService.getTrendingPosting();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Posting getPostingById(@PathVariable("id") long postingId) {
        return postingService.getPostingById(postingId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Posting createPosting(
            @RequestBody @Valid CreatePostingRequest request,
            @RequestAttribute User user
    ) {
        return postingService.createPosting(request, user);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Posting updatePosting(
            @RequestBody @Valid UpdatePostingRequest request,
            @RequestAttribute User user
    ) {
        return postingService.updatePosting(request, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePosting(
            @PathVariable("id") long postingId,
            @RequestAttribute User user
    ) {
        postingService.deletePosting(postingId, user);
    }
}
