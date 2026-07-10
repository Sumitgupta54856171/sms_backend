package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.BankDetail;
import com.example.schoolsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepo extends JpaRepository<BankDetail,Long> {
    boolean existsByStudent(Student student);

    Optional<BankDetail> findByStudent_Id(Long studentId);
}
