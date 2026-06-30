package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Studentrepo extends JpaRepository<Student,Long> {
}
