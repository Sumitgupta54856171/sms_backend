package com.example.schoolsystem.dto;




import com.example.schoolsystem.entity.Attendance;
import com.example.schoolsystem.entity.Enrollement_session;
import com.example.schoolsystem.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AttendanceResponseDTO {

    private Long attendanceId;
    private LocalDate attendanceDate;
    private String status;  // PRESENT or ABSENT
    private String studentName;
    private String rollNumber;
    private String scholarNo;
    private String grade;
    private Long studentId;

    // Constructor from Attendance entity
    public AttendanceResponseDTO(Attendance attendance) {
        if (attendance == null) return;

        this.attendanceId = attendance.getId();
        this.attendanceDate = attendance.getAttendanceDate();
        this.status = attendance.getStatus() != null ?
                attendance.getStatus().name() : null;
        this.grade = attendance.getGrade();
        this.studentId = attendance.getStudentId();

        // Get data through Enrollment -> Student
        if (attendance.getEnrollementId() != null) {
            Enrollement_session enrollment = attendance.getEnrollementId();
            this.rollNumber = enrollment.getRoll_no();

            // Get student details
            if (enrollment.getStudent() != null) {
                Student student = enrollment.getStudent();
                this.studentName = student.getName();
                this.scholarNo = student.getScholar_no();
                this.grade = enrollment.getClass_no();
            }
        }
    }
}
