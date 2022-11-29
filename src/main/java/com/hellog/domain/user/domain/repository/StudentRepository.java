package com.hellog.domain.user.domain.repository;

import com.hellog.domain.user.domain.entity.Student;
import com.hellog.domain.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByUser(User user);
    boolean existsByUser(User user);
}
