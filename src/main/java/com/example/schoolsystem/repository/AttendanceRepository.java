package com.example.schoolsystem.repository;

import com.example.schoolsystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {



    List<Attendance> findByAttendanceDate(LocalDate attendanceDate);







    boolean existsByStudentIdAndAttendanceDate(Long enrollmentId, LocalDate attendanceDate);
}
