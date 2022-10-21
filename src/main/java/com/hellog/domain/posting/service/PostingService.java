package com.hellog.domain.posting.service;

import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.posting.domain.repository.PostingRepository;
import com.hellog.domain.posting.exception.PostingNotFoundException;
import com.hellog.domain.posting.presentation.dto.request.CreatePostingRequest;
import com.hellog.domain.posting.presentation.dto.request.UpdatePostingRequest;
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

    @Transactional(readOnly = true)
    public List<Posting> getTrendingPosting() {
        return postingRepository.findAllByOrderByLikeCountDesc();
    }

    @Transactional(readOnly = true)
    public Posting getPostingById(long id) {
        return postingRepository.findById(id)
                .orElseThrow(() -> PostingNotFoundException.EXCEPTION);
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
