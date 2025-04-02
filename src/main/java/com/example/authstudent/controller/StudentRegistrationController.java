package com.example.authstudent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.authstudent.entity.StudentRegistration;
import com.example.authstudent.service.StudentRegistrationService;

import java.util.Map;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employe")
public class StudentRegistrationController {

    private final StudentRegistrationService employeService;

    public StudentRegistrationController(StudentRegistrationService employeService) {
        this.employeService = employeService;
    }

    @GetMapping
    public List<StudentRegistration> findAllEmploye() {
        return employeService.findAllEmploye();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<StudentRegistration>> findEmployeById(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(employeService.findById(id, token));
    }

    @PostMapping
    public StudentRegistration saveEmploye(@RequestBody StudentRegistration employe) {
        return employeService.saveEmploye(employe);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentRegistration> updateEmploye(@PathVariable("id") Long id, @RequestBody StudentRegistration employe, @RequestHeader("Authorization") String token) {
        StudentRegistration updatedEmploye = employeService.updateEmploye(id, employe, token);
        if (updatedEmploye != null) {
            return ResponseEntity.ok(updatedEmploye);
        }
        return ResponseEntity.status(403).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmploye(@PathVariable("id") Long id, @RequestHeader("Authorization") String token) {
        boolean isDeleted = employeService.deleteEmploye(id, token);
        if (isDeleted) {
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.status(403).body("Unauthorized to delete employee");
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerEmploye(@RequestBody StudentRegistration employe) {
        return ResponseEntity.ok(employeService.registerEmploye(employe)); 
    }
    
    @PostMapping("/login")
    public ResponseEntity<String> loginEmploye(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        return ResponseEntity.ok(employeService.loginEmploye(email, password));
    }
}
