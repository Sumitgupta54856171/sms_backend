package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Gmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Gmailrepo extends JpaRepository<Gmail,Long> {
}
