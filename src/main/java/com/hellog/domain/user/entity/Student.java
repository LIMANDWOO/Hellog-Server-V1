package com.hellog.domain.user.entity;

import com.hellog.domain.user.exception.StudentGeneratedInvalidException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.Year;

@Entity(name = "student")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    @Size(max = 45)
    private String name;

    @Column(nullable = false)
    private int generation;

    @Column(nullable = false)
    @Size(max = 255)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_user_id")
    private User user;

    @Builder
    public Student(String name, int generation, String description, User user) {

        checkValidGeneration(generation);

        this.name = name;
        this.generation = generation;
        this.description = description;
        this.user = user;
    }

    private void checkValidGeneration(int generation) {

        int year = Year.now().getValue();

        if(2015 + generation > year) {
            throw StudentGeneratedInvalidException.EXCEPTION;
        }
    }
}
