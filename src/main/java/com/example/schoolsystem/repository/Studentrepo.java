package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Student;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface Studentrepo extends JpaRepository<Student,Long> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Student s WHERE s.scholar_no = :scholar_no")
    boolean existsByScholarNo(@Param("scholar_no") String scholar_no);

    boolean existsByEmail(String email);
}

