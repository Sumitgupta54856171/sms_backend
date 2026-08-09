package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Salaryrepo extends JpaRepository<Salary,Long> {
}
