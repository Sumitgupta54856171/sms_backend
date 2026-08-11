package com.example.schoolsystem.service;


import com.example.schoolsystem.controller.Timetable;
import com.example.schoolsystem.dto.AttendanceResponseDTO;
import com.example.schoolsystem.dto.UpdateRollNodto;
import com.example.schoolsystem.entity.Attendance;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.repository.AttendanceRepository;
import com.example.schoolsystem.repository.Enrollementrepo;
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
    private final Enrollementrepo enrollementrepo;

    public ResponseEntity<?> getAttendancebyStudenId(Long studentId,Long sessionId){
        List<Attendance> at = attendanceRepository.findByStudentIdAndEnrollementId_Session_SessionId(studentId,sessionId);
        List<AttendanceResponseDTO> attendanceResponseDTOList = at.stream().map(AttendanceResponseDTO::new).toList();
        return ResponseEntity.ok(attendanceResponseDTOList);
    }
    public ResponseEntity<?> updateRollNo(List<UpdateRollNodto> updatelist, Long sessionId){
        List<Enrollement_session> rl = enrollementrepo.findAll();
        for(Enrollement_session e : rl){
            for(UpdateRollNodto u : updatelist){
                if(e.getStudent().getId().equals(u.getStudentId())){
                    e.setRoll_no(u.getRollNo());
                }
            }
        }
        enrollementrepo.saveAll(rl);
        return ResponseEntity.ok("successfully update");
    }
}
