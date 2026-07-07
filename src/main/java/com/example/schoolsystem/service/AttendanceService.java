package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.AttendanceRequestDTO;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.entity.AttendanceStatus;
import com.example.schoolsystem.entity.Attendance;
import com.example.schoolsystem.entity.ClassTeacherAssignment;
import com.example.schoolsystem.entity.Teacher;
import com.example.schoolsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AttendanceService {




   
    private final AttendanceRepository attendanceRepository;
    private final Studentrepo studentRepository;
    private final Teacherrepo teacherRepository;
    private final ClassTeacherAssignmentrepo classTeacherAssignmentrepo;
    private final Sessionrepo sessionRepository;
    private final Enrollementrepo enrollementrepo;


    public ResponseEntity<?> saveAttendance(List<AttendanceRequestDTO> attendanceRequests, Long sessionId) {
        for (AttendanceRequestDTO request : attendanceRequests) {
            if (attendanceRepository.existsByStudentIdAndAttendanceDate(request.getStudentId(), request.getAttendanceDate())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Attendance already exists for student ID " + request.getStudentId() + " on " + request.getAttendanceDate());
            }
            System.out.println("Attendance date: " + request.getAttendanceDate() + " Status: " + request.getStatus() + " Student ID: " + request.getStudentId() + " check the grade " + request.getGrade());

            if (request.getStatus() == null || request.getStudentId() == null) {
                return ResponseEntity.badRequest().body("Status and Student ID are required");
            }



            Attendance at = new Attendance();
            at.setAttendanceDate(request.getAttendanceDate() != null ? request.getAttendanceDate() : LocalDate.now());

            try {
                at.setStatus(AttendanceStatus.valueOf(request.getStatus().toUpperCase()));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body("Invalid attendance status: " + request.getStatus());
            }

            at.setEnrollementId(enrollementrepo.findByStudent_IdAndSession_Id(request.getStudentId(), sessionId));
            at.setGrade(request.getGrade());
            at.setStudentId(request.getStudentId());
            at.setAttendanceDate(request.getAttendanceDate() != null ? request.getAttendanceDate() : LocalDate.now());
            attendanceRepository.save(at);

        }
        return ResponseEntity.ok("All attendance records saved successfully");
    }

    public ResponseEntity<?> getAllAttendance(LocalDate date) {
        List<Attendance> attendanceList = attendanceRepository.findByAttendanceDate(date);
        List<AttendanceResponseDTO> response = attendanceList.stream()
                .map(AttendanceResponseDTO::new)
                .toList();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> editingAttendance(Long studentId, LocalDate date, String status) {
        List<Attendance> at = attendanceRepository.findByAttendanceDate(date);
        boolean found = false;
        for (Attendance a : at) {
            if (a.getStudentId().equals(studentId)) {
                try {
                    a.setStatus(AttendanceStatus.valueOf(status.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    return ResponseEntity.badRequest().body("Invalid attendance status: " + status);
                }
                attendanceRepository.save(a);
                found = true;
                break;
            }
        }

        if (!found) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Attendance record not found for student ID " + studentId + " on " + date);
        }

        return ResponseEntity.ok("Attendance edited successfully");
    }


}
