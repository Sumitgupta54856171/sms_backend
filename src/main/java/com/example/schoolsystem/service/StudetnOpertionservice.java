package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.EnrollmentRequestDto;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Invoice;
import com.example.schoolsystem.entity.Session;
import com.example.schoolsystem.entity.Student;
import com.example.schoolsystem.repository.Enrollementrepo;
import com.example.schoolsystem.repository.Invoicerepo;
import com.example.schoolsystem.repository.Sessionrepo;
import com.example.schoolsystem.repository.Studentrepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudetnOpertionservice {

    private final Enrollementrepo enrollementrepo;
    private final Sessionrepo sessionrepo;
    private final Studentrepo studentrepo;
    private final Invoicerepo invoicerepo;

    @Transactional
    public ResponseEntity<?> saveeEnrollment(List<EnrollmentRequestDto> enrollmentRequestDto, Long sessionId){


        Session session = sessionrepo.findById(sessionId).orElseThrow(() -> new RuntimeException("Session not found"));


        List<Enrollement_session> enrollments = new ArrayList<>();
        List<String> errors = new ArrayList<>();


        for (EnrollmentRequestDto studentId : enrollmentRequestDto) {
            Student student = studentrepo.findById(studentId.getStudentId()).orElse(null);
            System.out.println("check the student id and session id " + studentId.getStudentId()+ " "+sessionId);
            System.out.println("check the studentrollno and studetn totalfees"+studentId.getTotalfees()+" "+studentId.getRolNo());
           Session SessionId = sessionrepo.findByIs_currentIsTrue(true).get();
            System.out.println("check the session id and session id " + SessionId.getSessionId());
            List<Invoice> ls = invoicerepo.findAllByEnrollementSession_SessionIdAndStudent_Id(sessionId,studentId.getStudentId());
            
            Long totalpaid = ls.stream().mapToLong(Invoice::getAmount).sum();
            System.out.println("total paid "+totalpaid);

            if (student == null) {
                errors.add("Student with ID " + studentId.getStudentId() + " not found");
                continue;
            }


            if (enrollementrepo.existsByStudentAndSession_SessionId(student,SessionId.getSessionId())) {
                errors.add("Student " + studentId.getStudentId() + " already enrolled in this session");
                continue;
            }




            Long totalannulfees = enrollementrepo.findByStudent_IdAndSession_SessionId(student.getId(),sessionId).getTotal_fees();
            System.out.println("check this is work or not they show the total amount"+ totalannulfees);
            Enrollement_session enrollement_session = new Enrollement_session();
            enrollement_session.setStudent(student);
            enrollement_session.setSession(SessionId);
            enrollement_session.setClass_no(studentId.getClassNo());
            enrollement_session.setTotal_fees(studentId.getTotalfees());
            enrollement_session.setRoll_no(studentId.getRolNo());
            enrollement_session.setDueFees(totalannulfees - totalpaid);
            enrollments.add(enrollement_session);
        }

        if (!enrollments.isEmpty()) {
            System.out.println("Enrolled students: " + enrollments.getClass());
            enrollementrepo.saveAll(enrollments);
        }

        if (errors.isEmpty()) {
            return ResponseEntity.ok(enrollments.size() + " students enrolled successfully");
        } else {
            return ResponseEntity.badRequest().body(errors);
        }
    }


}
