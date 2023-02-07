package com.hellog.domain.notice.presentation;

import com.hellog.domain.notice.domain.entity.Notice;
import com.hellog.domain.notice.presentation.dto.request.CreateNoticeRequest;
import com.hellog.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import com.hellog.domain.notice.service.NoticeService;
import com.hellog.domain.user.domain.entity.User;
import com.hellog.global.auth.annotation.AuthenticationCheck;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("모든 공지사항을 불러옵니다")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Notice> getAllNotices() {
        return noticeService.getAllNotices();
    }

    @ApiOperation("공지사항 id로 공지사항을 조회합니다")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Notice getNoticeById(@PathVariable("id") long noticeId) {
        return noticeService.getNoticeById(noticeId);
    }

    @ApiOperation("공지사항을 생성합니다 (관리자 전용)")
    @AuthenticationCheck
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Notice createNotice(
            @RequestBody @Valid CreateNoticeRequest request,
            @RequestAttribute User user
    ) {
        return noticeService.createNotice(request, user);
    }

    @ApiOperation("공지사항을 수정합니다 (관리자 전용)")
    @AuthenticationCheck
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Notice updateNotice(
            @RequestBody @Valid UpdateNoticeRequest request,
            @RequestAttribute User user
    ) {
        return noticeService.updateNotice(request, user);
    }

    @ApiOperation("공지사항을 삭제합니다 (관리자 전용)")
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
