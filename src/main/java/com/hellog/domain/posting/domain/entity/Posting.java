package com.hellog.domain.posting.domain.entity;

import com.hellog.domain.posting.exception.PostingForbiddenException;
import com.hellog.domain.posting.domain.type.PostingStatus;
import com.hellog.domain.user.domain.entity.Student;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "posting")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Posting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(max = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PostingStatus status;

    @Column(nullable = false)
    private long likeCount;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedDate;

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

    public void updatePosting(String title, String content, String summary, Student student) {
        if (!this.student.equals(student)) {
            throw PostingForbiddenException.EXCEPTION;
        }
        this.title = title;
        this.content = content;
        this.summary = summary;
    }

    @Builder
    public Posting(String title, String content, String summary, Student student) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.student = student;
        this.status = PostingStatus.ACTIVE;
        this.likeCount = 0;
    }
}