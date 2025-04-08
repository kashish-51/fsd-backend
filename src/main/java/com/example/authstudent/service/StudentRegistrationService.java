package com.example.authstudent.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.authstudent.entity.StudentRegistration;

public interface StudentRegistrationService {
    List<StudentRegistration> findAllEmploye();
    Optional<StudentRegistration> findById(Long id, String token);
    StudentRegistration saveEmploye(StudentRegistration employe);
    StudentRegistration updateProfile(StudentRegistration existingUser, StudentRegistration updatedData);
    boolean deleteEmploye(Long id, String token);
    Map<String, String> registerEmploye(StudentRegistration employe);  // 🔹 Updated return type
    String loginEmploye(String email, String password);
    String validateToken(String token);
    Optional<StudentRegistration> findByEmail(String email);
}
