package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotorRepo extends JpaRepository<Photo,Long> {
    Object findByStudent_Id(Long studentId);
}
