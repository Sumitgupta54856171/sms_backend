package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Messagerepo extends JpaRepository<Message,Long> {
}
