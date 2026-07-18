package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Noticerepo extends JpaRepository<Notice,Long> {
}
