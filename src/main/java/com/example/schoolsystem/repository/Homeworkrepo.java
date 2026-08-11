package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Homeworkrepo extends JpaRepository<Homework,Long> {
}
