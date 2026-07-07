package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Sessionrepo extends JpaRepository<Session,Long> {
    // Session findBySessionId(Long sessionId);

    @Query("SELECT s FROM Session s WHERE s.is_active = :is_active")
    Optional<Session> findByIs_Active(boolean is_active);
    
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Session s WHERE s.is_active = :is_active")
    boolean existsByIs_Active(boolean is_active);

}
