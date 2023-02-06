package com.hellog.domain.user.presentation.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.posting.domain.entity.Posting;
import com.hellog.domain.user.domain.type.AuthType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UserResponse {

    private long id;
    private String name;
    private String description;
    private String profileImage;
    private AuthType authType;
    private List<Posting> postings;
}
