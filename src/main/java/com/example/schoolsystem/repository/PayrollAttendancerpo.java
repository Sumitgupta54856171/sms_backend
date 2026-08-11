package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.PayrollAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PayrollAttendancerpo extends JpaRepository<PayrollAttendance,Long> {

    List<PayrollAttendance> findAllByDateBetween(LocalDate start,LocalDate end);

    Optional<PayrollAttendance> findByDateAndTeacherId(LocalDate date, Long teacherId);
}
