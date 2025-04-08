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

    @GetMapping("/me")
    public ResponseEntity<?> getLoggedInUser(@RequestHeader("Authorization") String token) {
        try {
            String email = employeService.validateToken(token);
            Optional<StudentRegistration> user = employeService.findByEmail(email);
    
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
    }
    

    @PostMapping
    public StudentRegistration saveEmploye(@RequestBody StudentRegistration employe) {
        return employeService.saveEmploye(employe);
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token, @RequestBody StudentRegistration updatedData) {
        try {
            String email = employeService.validateToken(token);
            Optional<StudentRegistration> userOptional = employeService.findByEmail(email);
    
            if (userOptional.isPresent()) {
                StudentRegistration updatedUser = employeService.updateProfile(userOptional.get(), updatedData);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.status(404).body("User not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid or expired token");
        }
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
