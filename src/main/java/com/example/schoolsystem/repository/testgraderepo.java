package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.TestGrade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface testgraderepo extends JpaRepository<TestGrade,Long> {
}
