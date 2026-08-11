package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.ElectSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ElectSubjectrepo extends JpaRepository<ElectSubject,Long> {

    List<ElectSubject> findAllBySessionId(Long sessionId);
}
