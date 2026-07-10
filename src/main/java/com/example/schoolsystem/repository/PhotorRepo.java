package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotorRepo extends JpaRepository<Photo,Long> {
    Optional<Photo> findByStudent_Id(Long studentId);

    void deleteByStudent_Id(Long studentId);
}
