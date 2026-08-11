package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Questionattempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Questionattemptrepo extends JpaRepository<Questionattempt,Long> {
}
