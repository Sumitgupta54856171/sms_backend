package com.example.schoolsystem.repository;


import com.example.schoolsystem.entity.TeacherPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TeacherPlandrepo extends JpaRepository<TeacherPlan,Long> {


    TeacherPlan findByTeacher_Id(Long teacherId);

    List<TeacherPlan> findByDateAndTeacher_Id(LocalDate date, Long teacherId);
}
