package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.BankDetailsRequestDto;
import com.example.schoolsystem.dto.StudentClassResponse;
import com.example.schoolsystem.dto.Studentdto;
import com.example.schoolsystem.entity.*;
import com.example.schoolsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class Studentservice {

    private final Studentrepo studentrepo;
    private final Enrollementrepo enrollementrepo;
    private final Sessionrepo sessionrepo;
    private final BankRepo bankRepo;
    private final Invoicerepo invoicerepo;
    private final ClassTeacherAssignmentrepo classTeacherAssignmentrepo;


    public ResponseEntity<?> saveStudentData(Studentdto studentDto, Long sessionId) throws Exception {


        if (studentrepo.existsByScholarNo(studentDto.getScholar_no())) {
            throw new UsernameNotFoundException("Student already exists with scholar number: " + studentDto.getScholar_no());
        }
        if (studentrepo.existsByEmail(studentDto.getEmail())) {
            throw new UsernameNotFoundException("Student already exists with email: " + studentDto.getEmail());
        }
        System.out.println("student name is present" + studentDto.getName() + " " + studentDto.getScholar_no() + "check status of student" + " " + studentDto.getStatus());

        Student student = new Student();
        student.setName(studentDto.getName());
        student.setEmail(studentDto.getEmail());
        student.setScholar_no(studentDto.getScholar_no());
        student.setSssmid(studentDto.getSssmid());
        student.setAadhaar(studentDto.getAadhaar());
        student.setGender(studentDto.getGender());
        student.setCategory(studentDto.getCategory());
        student.setDob(studentDto.getDob());
        student.setPhone(studentDto.getPhone());
        student.setFather_name(studentDto.getFather_name());
        student.setMother_name(studentDto.getMother_name());
        if (studentDto.getStatus() == null) {
            student.setStatus(Status.active);
        } else {
            student.setStatus(studentDto.getStatus());
        }

        student = studentrepo.save(student);
        System.out.println("student saved successfully" + student.getId());

        Optional<Session> activeSession = sessionrepo.findByIs_Active(true);
        if (activeSession.isPresent()) {
            Enrollement_session enrollment = new Enrollement_session();
            enrollment.setStudent(student);
            enrollment.setSession(activeSession.get());
            enrollment.setClass_no(studentDto.getClass_no());
            enrollment.setRoll_no(studentDto.getRoll_no());
            enrollment.setTotal_fees(studentDto.getTotal_fees());
            enrollment = enrollementrepo.save(enrollment);
            enrollment.setTotal_fees( studentDto.getTotal_fees());
            enrollementrepo.save(enrollment);
        }

        return ResponseEntity.ok("Student is successfully saved");
    }

    public ResponseEntity<?> fetchStudentData(Long sessionId) throws Exception {

        List<Student> students = studentrepo.findAll();
        List<Map<String, Object>> responseList = new ArrayList<>();
        for (Student student : students) {
            System.out.println("start fetch student data" + student.getId());

            Map<String, Object> studentMap = new HashMap<>();
            studentMap.put("id", student.getId());
            studentMap.put("name", student.getName());
            studentMap.put("email", student.getEmail());
            studentMap.put("scholar_no", student.getScholar_no());
            studentMap.put("status", student.getStatus());

            List<Enrollement_session> studentenrollment = enrollementrepo.findAllByStudent_IdAndSession_Id(student.getId(), sessionId);

            studentMap.put("enrollment", studentenrollment);
            responseList.add(studentMap);
        }

        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("data", responseList);
        finalResponse.put("success", "student fetched successfully");

        return ResponseEntity.ok(finalResponse);
    }

    public ResponseEntity<?> deletStudent(Long id) throws  Exception{
        studentrepo.deleteById(id);
       return  ResponseEntity.ok("student deleted successfully");
    }

    public ResponseEntity<?> updateStudent(Student student) throws Exception{
        studentrepo.save(student);
        return ResponseEntity.ok("student updated successfully");
    }
    public  ResponseEntity<?> getStudentByClass(String class_no,Long sessionId){
        System.out.println("class no is "+class_no);
        String no = class_no.replace("Grade ","");
        System.out.println("no is "+no);
        List<Enrollement_session> st = enrollementrepo.findAllByClass_noAndSessionId(no,sessionId);
        System.out.println("student list is "+st);

        return ResponseEntity.ok(st);
    }

    public ResponseEntity<?> saveBankDetail(BankDetailsRequestDto bankDetailsRequestDto){
        if(bankRepo.existsByStudent(studentrepo.findById(bankDetailsRequestDto.getStudentId()).get())){
            return ResponseEntity.ok("bank detail already exists");
        }

        BankDetail bk = new BankDetail();
        bk.setAccountHolderName(bankDetailsRequestDto.getAccountHolder());
        bk.setAccountNumber(bankDetailsRequestDto.getAccountNo());
        bk.setStudent(studentrepo.findById(bankDetailsRequestDto.getStudentId()).get());
        bk.setBranchName(bankDetailsRequestDto.getBranch());
        bk.setBankName(bankDetailsRequestDto.getBankName());
        bk.setIfscCode(bankDetailsRequestDto.getIfscCode());
        BankDetail sv =bankRepo.save(bk);
        return ResponseEntity.ok("bank detail saved successfully");

    }
    public ResponseEntity<?> getStudentbyclass(String class_no){
        List<Enrollement_session> st = enrollementrepo.findAllByClass_no(class_no);
        List<StudentClassResponse> response = st.stream().map(s->new StudentClassResponse(s.getClass_no(),s.getRoll_no(),s.getStudent().getName(),s.getStudent().getScholar_no())).collect(Collectors.toList());

        ClassTeacherAssignment cl = classTeacherAssignmentrepo.findByGradeClass(class_no);
        String ct = cl.getTeacher().getFullName();

        Map<String,Object> map = new HashMap<>();
        map.put("studentdetail",response);
        map.put("ct",ct);
        map.put("success","student detail fetched successfully");
        return ResponseEntity.ok(map);
    }

}
