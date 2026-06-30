package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Teacherrepo extends JpaRepository<Teacher,Long> {
}
