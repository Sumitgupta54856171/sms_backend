package com.example.schoolsystem.controller;

import com.example.schoolsystem.entity.PayrollAttendance;
import com.example.schoolsystem.entity.Salary;
import com.example.schoolsystem.entity.Salaryslip;
import com.example.schoolsystem.service.PayrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    // === Payroll Attendance ===

    @PostMapping("/attendance/save")
    public ResponseEntity<?> saveAttendance(@RequestBody List<PayrollAttendance> payrollAttendances) {
        return payrollService.saveAttendance(payrollAttendances);
    }

    @GetMapping("/attendance/date/{startDate}/{endDate}")
    public ResponseEntity<List<PayrollAttendance>> getAttendanceByDate(
            @PathVariable("startDate") LocalDate startDate,
            @PathVariable("endDate") LocalDate endDate) {
        return ResponseEntity.ok(payrollService.getAttendancebydate(startDate, endDate));
    }

    @PutMapping("/attendance/update")
    public ResponseEntity<?> updateAttendance(@RequestBody List<PayrollAttendance> payrollAttendances) {
        return payrollService.updateAttendance(payrollAttendances);
    }

    // === Salary ===

    @PostMapping("/salary/save")
    public ResponseEntity<?> saveSalary(@RequestBody Salary salary) {
        return payrollService.saveSalary(salary);
    }

    @GetMapping("/salary/all")
    public ResponseEntity<List<Salary>> getAllSalaries() {
        return ResponseEntity.ok(payrollService.getAllSalaries());
    }

    @GetMapping("/salary/{id}")
    public ResponseEntity<?> getSalaryById(@PathVariable("id") Long id) {
        return payrollService.getSalaryById(id);
    }

    // === Salaryslip ===

    @PostMapping("/salaryslip/save")
    public ResponseEntity<?> saveSalaryslip(@RequestBody List<Salaryslip> salaryslip) {
        return payrollService.saveSalaryslip(salaryslip);
    }

    @GetMapping("/salaryslip/all")
    public ResponseEntity<List<Salaryslip>> getAllSalaryslips() {
        return ResponseEntity.ok(payrollService.getAllSalaryslips());
    }

    @GetMapping("/salaryslip/{id}")
    public ResponseEntity<?> getSalaryslipById(@PathVariable("id") Long id) {
        return payrollService.getSalaryslipById(id);
    }
}
