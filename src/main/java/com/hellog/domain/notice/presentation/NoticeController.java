package com.hellog.domain.notice.presentation;

import com.hellog.domain.notice.domain.entity.Notice;
import com.hellog.domain.notice.presentation.dto.request.CreateNoticeRequest;
import com.hellog.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import com.hellog.domain.notice.service.NoticeService;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.annotation.AuthenticationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Notice getNoticeById(@PathVariable("id") long noticeId) {
        return noticeService.getNoticeById(noticeId);
    }

    @AuthenticationCheck
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notice createNotice(
            @RequestBody @Valid CreateNoticeRequest request,
            @RequestAttribute User user
    ) {
        return noticeService.createNotice(request, user);
    }

    @AuthenticationCheck
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Notice updateNotice(
            @RequestBody @Valid UpdateNoticeRequest request,
            @RequestAttribute User user
    ) {
        return noticeService.updateNotice(request, user);
    }

    @AuthenticationCheck
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteNotice(
            @PathVariable("id") long noticeId,
            @RequestAttribute User user
    ) {
        noticeService.deleteNotice(noticeId, user);
    }
}
