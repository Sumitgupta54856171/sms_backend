package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Eventrepo extends JpaRepository<Event,Long> {
}
