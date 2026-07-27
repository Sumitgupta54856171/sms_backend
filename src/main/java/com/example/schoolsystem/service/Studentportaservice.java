package com.example.schoolsystem.service;


import com.example.schoolsystem.controller.Timetable;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.entity.Attendance;
import com.example.schoolsystem.repository.AttendanceRepository;
import com.example.schoolsystem.repository.Feerepo;
import com.example.schoolsystem.repository.Timetablerepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class Studentportaservice {
    private final AttendanceRepository attendanceRepository;
    private final Feerepo feerepo;
    private final Timetablerepo timetablerepo;

    public ResponseEntity<?> getAttendancebyStudenId(Long studentId,Long sessionId){
        List<Attendance> at = attendanceRepository.findByStudentIdAndEnrollementId_Session_SessionId(studentId,sessionId);
        List<AttendanceResponseDTO> attendanceResponseDTOList = at.stream().map(AttendanceResponseDTO::new).toList();
        return ResponseEntity.ok(attendanceResponseDTOList);
    }
}
