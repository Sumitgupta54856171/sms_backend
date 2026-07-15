package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ExamTimeTable;
import com.example.schoolsystem.entity.ExanGrade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Examgraderepo extends JpaRepository<ExanGrade,Long> {
}
