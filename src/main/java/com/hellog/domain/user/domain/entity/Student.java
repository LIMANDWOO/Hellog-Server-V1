package com.hellog.domain.user.domain.entity;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.hellog.domain.user.exception.StudentGenerationInvalidException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Year;

@Entity
@Table(name = "tbl_student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Student {

    @Id
    private long id;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    @Size(max = 255)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User user;

    private void checkValidGeneration(int generation) {

        int year = Year.now().getValue();

        if(2015 + generation > year) {
            throw StudentGenerationInvalidException.EXCEPTION;
        }
    }

    @Builder
    public Student(long userId, int generation, String description, User user) {

        checkValidGeneration(generation);

        this.id = userId;
        this.generation = generation;
        this.description = description;
        this.user = user;
    }
}
