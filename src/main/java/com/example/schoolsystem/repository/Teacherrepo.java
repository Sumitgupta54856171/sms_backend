package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Teacherrepo extends JpaRepository<Teacher,Long> {
    boolean existsByEmail(String email);


    Teacher findByEmail(String email);

}
