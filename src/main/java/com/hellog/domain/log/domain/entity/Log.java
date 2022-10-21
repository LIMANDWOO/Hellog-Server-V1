package com.hellog.domain.log.domain.entity;

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
public class Log extends BaseTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String errorName;

    @Column(nullable = false)
    private int statusCode;

    @Builder
    public Log(String errorName, HttpStatus status) {
        this.errorName = errorName;
        this.statusCode = status.value();
    }
}
