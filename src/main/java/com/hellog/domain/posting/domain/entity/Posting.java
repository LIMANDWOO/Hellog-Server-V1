package com.hellog.domain.posting.domain.entity;

import com.hellog.domain.posting.exception.PostingNotOwnerException;
import com.hellog.domain.posting.domain.type.PostingStatus;
import com.hellog.domain.user.domain.entity.Student;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_posting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Posting extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(max = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private String thumbnailUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PostingStatus status;

    @Column(nullable = false)
    private long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_student_id")
    private Student student;

    @Column(nullable = false)
    @Size(max = 255)
    private String summary;

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }

    public void checkCanManage(Student student) {
        if (this.student.getId() != student.getId()) {
            throw PostingNotOwnerException.EXCEPTION;
        }
    }

    public void updatePosting(String title, String content, String summary, String thumbnailUrl, Student student) {
        checkCanManage(student);
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.thumbnailUrl = thumbnailUrl;
    }

    @Builder
    public Posting(String title, String content, String summary, Student student, String thumbnailUrl) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.student = student;
        this.thumbnailUrl = thumbnailUrl;
        this.status = PostingStatus.ACTIVE;
        this.likeCount = 0;
    }
}
