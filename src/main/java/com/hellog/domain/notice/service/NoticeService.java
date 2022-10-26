package com.hellog.domain.notice.service;

import com.hellog.domain.notice.domain.entity.Notice;
import com.hellog.domain.notice.domain.repository.NoticeRepository;
import com.hellog.domain.notice.exception.NoticeNotFoundException;
import com.hellog.domain.notice.presentation.dto.request.CreateNoticeRequest;
import com.hellog.domain.notice.presentation.dto.request.UpdateNoticeRequest;
import com.hellog.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<Notice> getAllNotices() {
        return noticeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Notice getNoticeById(long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
    }

    @Transactional(rollbackFor = Exception.class)
    public Notice createNotice(CreateNoticeRequest request, User user) {

        Notice notice = Notice.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        return noticeRepository.save(notice);
    }

    @Transactional(rollbackFor = Exception.class)
    public Notice updateNotice(UpdateNoticeRequest request, User user) {

        Notice notice = noticeRepository.findById(request.getNoticeId())
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
        notice.updateNotice(request.getTitle(), request.getContent(), user);
        return noticeRepository.save(notice);
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteNotice(long noticeId, User user) {

        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> NoticeNotFoundException.EXCEPTION);
        notice.checkAdmin(user);
        noticeRepository.delete(notice);
    }
}
