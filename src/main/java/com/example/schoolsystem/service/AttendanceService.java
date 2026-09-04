package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.AttendanceRequestDTO;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.entity.*;
import com.example.schoolsystem.repository.*;
import com.example.schoolsystem.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.management.RuntimeErrorException;
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
    private final JwtUtils jwtUtils;
    private final Userrepo userrepo;


    public ResponseEntity<?> saveAttendance(List<AttendanceRequestDTO> attendanceRequests, Long sessionId) {
        for (AttendanceRequestDTO request : attendanceRequests) {
            if (attendanceRepository.existsByStudentIdAndAttendanceDate(request.getStudentId(), request.getAttendanceDate())) {
               continue;
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

            at.setEnrollementId(enrollementrepo.findByStudent_IdAndSession_SessionId(request.getStudentId(), sessionId));
            at.setGrade(request.getGrade());
            at.setStudentId(request.getStudentId());
            at.setAttendanceDate(request.getAttendanceDate() != null ? request.getAttendanceDate() : LocalDate.now());
            attendanceRepository.save(at);

        }
        return ResponseEntity.ok("All attendance records saved successfully");
    }

    public ResponseEntity<?> getAllAttendance(LocalDate date,Long sessionId,String token) {

        List<Attendance> attendanceList = attendanceRepository.findAllByAttendanceDateAndEnrollementId_Session_SessionId(date,sessionId);
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

    public ResponseEntity<?> getAttendanceclassbyclassteacher(String token){
        String tokeid = jwtUtils.getUserNameFromJwtToken(token);
        Teacher teachervalue = teacherRepository.findByEmail(tokeid);

        ClassTeacherAssignment cla = classTeacherAssignmentrepo.findByTeacher(teachervalue);

        return ResponseEntity.ok(cla.getGradeClass());

    }
    public ResponseEntity<?> getAttendancebydate(LocalDate startdate,LocalDate enddate,Long sessionId){
        if(startdate == null || enddate ==null){
            return ResponseEntity.ok("please share the valueof start date and enddate");
        }
        List<Attendance> at = attendanceRepository.findByAttendanceDateBetweenAndEnrollementId_Session_SessionId(startdate, enddate, sessionId);
        List<AttendanceResponseDTO> response = at.stream()
                .map(AttendanceResponseDTO::new)
                .toList();

        return ResponseEntity.ok(response);

    }



}
