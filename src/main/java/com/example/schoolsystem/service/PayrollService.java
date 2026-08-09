package com.example.schoolsystem.service;

import com.example.schoolsystem.entity.PayrollAttendance;
import com.example.schoolsystem.entity.Salary;
import com.example.schoolsystem.entity.Salaryslip;
import com.example.schoolsystem.repository.PayrollAttendancerpo;
import com.example.schoolsystem.repository.Salaryrepo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PayrollService {

    private final PayrollAttendancerpo payrollAttendancerpo;
    private final Salaryrepo salaryrepo;
    private final com.example.schoolsystem.repository.Salaryslip salaryslipRepo;

    public ResponseEntity<?> saveAttendance(List<PayrollAttendance> payrollAttendance) {
        payrollAttendancerpo.saveAll(payrollAttendance);
        return ResponseEntity.ok("save attendance of teachers successfully");
    }

    public List<PayrollAttendance> getAttendancebydate(LocalDate start, LocalDate end) {
        return payrollAttendancerpo.findAllByDateBetween(start, end);
    }

    public ResponseEntity<?> updateAttendance(List<PayrollAttendance> payrollAttendances) {
        for (PayrollAttendance payrollAttendance : payrollAttendances) {
            if (payrollAttendance.getPayAttendanceID() != 0) {
                payrollAttendancerpo.findById((long) payrollAttendance.getPayAttendanceID())
                        .ifPresent(existingAttendance -> {
                            existingAttendance.setMachineId(payrollAttendance.getMachineId());
                            existingAttendance.setTodaydate(payrollAttendance.getTodaydate());
                            existingAttendance.setCheckin(payrollAttendance.getCheckin());
                            existingAttendance.setCheckout(payrollAttendance.getCheckout());
                            existingAttendance.setStatus(payrollAttendance.getStatus());
                            existingAttendance.setWorkhours(payrollAttendance.getWorkhours());
                            existingAttendance.setRemarks(payrollAttendance.getRemarks());
                            existingAttendance.setCompanyName(payrollAttendance.getCompanyName());
                            existingAttendance.setTeacher(payrollAttendance.getTeacher());
                            existingAttendance.setDate(payrollAttendance.getDate());
                            payrollAttendancerpo.save(existingAttendance);
                        });
            } else {
                if (payrollAttendance.getDate() != null && payrollAttendance.getTeacher() != null) {
                    payrollAttendancerpo.findByDateAndTeacherId(payrollAttendance.getDate(), payrollAttendance.getTeacher().getId())
                            .ifPresentOrElse(existingAttendance -> {
                                existingAttendance.setMachineId(payrollAttendance.getMachineId());
                                existingAttendance.setTodaydate(payrollAttendance.getTodaydate());
                                existingAttendance.setCheckin(payrollAttendance.getCheckin());
                                existingAttendance.setCheckout(payrollAttendance.getCheckout());
                                existingAttendance.setStatus(payrollAttendance.getStatus());
                                existingAttendance.setWorkhours(payrollAttendance.getWorkhours());
                                existingAttendance.setRemarks(payrollAttendance.getRemarks());
                                existingAttendance.setCompanyName(payrollAttendance.getCompanyName());
                                existingAttendance.setDate(payrollAttendance.getDate());
                                payrollAttendancerpo.save(existingAttendance);
                            }, () -> payrollAttendancerpo.save(payrollAttendance));
                } else {
                    payrollAttendancerpo.save(payrollAttendance);
                }
            }
        }
        return ResponseEntity.ok("Attendance updated successfully");
    }

    // === Salary methods ===

    public ResponseEntity<?> saveSalary(Salary salary) {
        System.out.println(salary.getTeacher().getId());
        System.out.println();
        salaryrepo.save(salary);
        return ResponseEntity.ok("Salary saved successfully");
    }

    public List<Salary> getAllSalaries() {
        return salaryrepo.findAll();
    }

    public ResponseEntity<?> getSalaryById(Long id) {
        return salaryrepo.findById(id)
                .map(salary -> ResponseEntity.ok((Object) salary))
                .orElse(ResponseEntity.notFound().build());
    }

    // === Salaryslip methods ===

    public ResponseEntity<?> saveSalaryslip(List<Salaryslip> salaryslip) {
        for (Salaryslip slip : salaryslip) {
            salaryslipRepo.save(slip);
        }
        
        return ResponseEntity.ok("Salaryslip saved successfully");
    }

    public List<Salaryslip> getAllSalaryslips() {
        return salaryslipRepo.findAll();
    }

    public ResponseEntity<?> getSalaryslipById(Long id) {
        return salaryslipRepo.findById(id)
                .map(slip -> ResponseEntity.ok((Object) slip))
                .orElse(ResponseEntity.notFound().build());
    }
}
