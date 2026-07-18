package com.example.schoolsystem.service;


import com.example.schoolsystem.dto.BankDetailsRequestDto;
import com.example.schoolsystem.dto.StudentClassResponse;
import com.example.schoolsystem.dto.StudentListdto;
import com.example.schoolsystem.dto.Studentdto;
import com.example.schoolsystem.entity.*;
import com.example.schoolsystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    private final PhotorRepo photorRepo;



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
        student.setAddress(studentDto.getAddress());
        student.setPenId(studentDto.getPenId());
        student.setApaarId(studentDto.getApaarId());
        if (studentDto.getStatus() == null) {
            student.setStatus(Status.active);
        } else {
            student.setStatus(studentDto.getStatus());
        }

        student = studentrepo.save(student);
        System.out.println("student saved successfully" + student.getId());

        Optional<Session> activeSession = sessionrepo.findByIs_currentIsTrue(true);
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

            List<Enrollement_session> studentenrollment = enrollementrepo.findAllByStudent_IdAndSession_SessionId(student.getId(), sessionId);

            studentMap.put("enrollment", studentenrollment);
            responseList.add(studentMap);
        }

        Map<String, Object> finalResponse = new HashMap<>();
        finalResponse.put("data", responseList);
        finalResponse.put("success", "student fetched successfully");

        return ResponseEntity.ok(finalResponse);
    }

    

    public ResponseEntity<?> updateStudent(Student student) throws Exception{
        studentrepo.save(student);
        return ResponseEntity.ok("student updated successfully");
    }
    public  ResponseEntity<?> getStudentByClassAndSessionId(String class_no,Long sessionId){
        System.out.println("class no is "+class_no);
        String no = class_no.replace("Grade ","");
        System.out.println("no is "+no);
        List<Enrollement_session> st = enrollementrepo.findAllByClass_noAndSession_SessionId(no,sessionId);
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
    public ResponseEntity<?> getStudentbyclass(String class_no,Long sessionId){

        List<Enrollement_session> st = enrollementrepo.findAllByClass_noAndSession_SessionId(class_no.replace("Grade ",""),sessionId);
        List<StudentClassResponse> response = st.stream().map(s->new StudentClassResponse(s.getClass_no(),s.getRoll_no(),s.getStudent().getName(),s.getStudent().getScholar_no(),s.getStudent().getId())).collect(Collectors.toList());



        Map<String,Object> map = new HashMap<>();
        map.put("studentdetail",response);

        map.put("success","student detail fetched successfully");
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<?> updateStudentdetail(Student student) throws Exception{
        studentrepo.save(student);
        return ResponseEntity.ok("update data");
    }
    public ResponseEntity<?> updateBankDetail(BankDetail bankDetail)throws Exception{
        bankRepo.save(bankDetail);
        return ResponseEntity.ok("updated bank details");
    }


    public ResponseEntity<?> getstudentdetail(Long studentId)throws Exception{
        Student st = studentrepo.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + studentId));
        BankDetail bk = bankRepo.findByStudent_Id(studentId).orElse(null);
        Photo ph = photorRepo.findByStudent_Id(studentId).orElse(null);
        Map<String,Object> response = new  HashMap<>();

        response.put("student",st);
        response.put("bank",bk);
        response.put("photo",ph);

        return ResponseEntity.ok(response);

    }

    public  ResponseEntity<?> getAllStudent(){
        List<Student> st = studentrepo.findAll();
        List<StudentListdto> stl = st.stream().map(s -> new StudentListdto(s.getName(), s.getId(), s.getScholar_no(), s.getFather_name(), s.getMother_name(), s.getStatus())).collect(Collectors.toList());
        return ResponseEntity.ok(stl);
    }
    public ResponseEntity<?> getclassandrollno(Long studentId,Long sessionId){
        Enrollement_session class_no = enrollementrepo.findByStudent_IdAndSession_SessionId(studentId,sessionId);
        String cls = class_no.getClass_no();
        String rl = class_no.getRoll_no();
        Map<String,Object> responselist = new HashMap<>();
        responselist.put("class_no",cls);
        responselist.put("roll_no",rl);
        return ResponseEntity.ok(responselist);
    }



}
