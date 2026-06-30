package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Sessionrepo extends JpaRepository<Session,Long> {
    // Session findBySessionId(Long sessionId);
}
