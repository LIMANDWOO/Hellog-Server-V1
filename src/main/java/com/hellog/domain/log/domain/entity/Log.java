package com.hellog.domain.log.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.global.jpa.BaseTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.persistence.*;

@Entity
@Table(name = "tbl_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Log extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String errorName;

    @Column(nullable = false)
    private int statusCode;

    @Column(nullable = false)
    private String message;

    @Builder
    public Log(String errorName, HttpStatus status, String message) {
        this.errorName = errorName;
        this.statusCode = status.value();
        this.message = message;
    }
}
