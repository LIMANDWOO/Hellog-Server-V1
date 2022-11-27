package com.hellog.domain.posting.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.comment.domain.entity.Comment;
import com.hellog.domain.posting.domain.type.PostingStatus;
import com.hellog.domain.user.domain.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostingResponse {

    private long id;
    private String title;
    private String content;
    private String thumbnailUrl;
    private PostingStatus status;
    private long likeCount;
    private Student student;
    private String summary;
    private List<Comment> comments;
    private boolean liked;
}
