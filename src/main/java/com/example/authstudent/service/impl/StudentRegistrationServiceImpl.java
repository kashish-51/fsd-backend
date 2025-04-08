package com.example.authstudent.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.authstudent.entity.StudentRegistration;
import com.example.authstudent.repository.StudentRegistrationRepository;
import com.example.authstudent.service.StudentRegistrationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class StudentRegistrationServiceImpl implements StudentRegistrationService {

    private final StudentRegistrationRepository employeRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final byte[] SECRET_KEY = "mySuperSecretKeyForJWTGeneration".getBytes(StandardCharsets.UTF_8);

    public StudentRegistrationServiceImpl(StudentRegistrationRepository employeRepository) {
        this.employeRepository = employeRepository;
    }

    @Override
    public List<StudentRegistration> findAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public Optional<StudentRegistration> findById(Long id, String token) {
        try {
            String email = validateToken(token);
            Optional<StudentRegistration> employe = employeRepository.findById(id);
            if (employe.isPresent() && employe.get().getEmail().equals(email)) {
                return employe;
            }
        } catch (Exception e) {
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public StudentRegistration saveEmploye(StudentRegistration employe) {
        return employeRepository.save(employe);
    }

    @Override
    public StudentRegistration updateProfile(StudentRegistration existingUser, StudentRegistration updatedData) {
        existingUser.setName(updatedData.getName());
        existingUser.setPhoneNumber(updatedData.getPhoneNumber());
        existingUser.setCollegeName(updatedData.getCollegeName());
        existingUser.setEnrollmentNumber(updatedData.getEnrollmentNumber());
        existingUser.setDegree(updatedData.getDegree());
        existingUser.setYearOfStudy(updatedData.getYearOfStudy());
        existingUser.setCity(updatedData.getCity());
        existingUser.setSpecialAssistanceRequired(updatedData.isSpecialAssistanceRequired());
        existingUser.setGender(updatedData.getGender());
        existingUser.setDateOfBirth(updatedData.getDateOfBirth());
        existingUser.setAddress(updatedData.getAddress());
    
        return employeRepository.save(existingUser);
    }
    

    @Override
    public boolean deleteEmploye(Long id, String token) {
        try {
            String email = validateToken(token);
            Optional<StudentRegistration> employe = employeRepository.findById(id);
            if (employe.isPresent()) {
                StudentRegistration student = employe.get();
                if (!student.getEmail().equals(email)) {
                    return false; // Prevent deletion if token email doesn't match student's email
                }
                employeRepository.deleteById(id);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }
    

    @Override
    public Map<String, String> registerEmploye(StudentRegistration employe) {
        Map<String, String> response = new HashMap<>();

        if (employeRepository.findByEmail(employe.getEmail()).isPresent()) {
            response.put("message", "Email already exists!");
            return response;
        }

        employe.setPassword(passwordEncoder.encode(employe.getPassword()));
        employeRepository.save(employe);

        String token = generateToken(employe);
        response.put("message", "Employee registered successfully");
        response.put("token", token);
        
        return response;
    }

    @Override
    public String loginEmploye(String email, String password) {
        Optional<StudentRegistration> employe = employeRepository.findByEmail(email);

        if (employe.isPresent()) {
            boolean passwordMatch = passwordEncoder.matches(password, employe.get().getPassword());
            if (passwordMatch) {
                return generateToken(employe.get());
            }
        }
        return "Invalid email or password";
    }

    private String generateToken(StudentRegistration employe) {
        return Jwts.builder()
                .setSubject(employe.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public String validateToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7); // Strip "Bearer " prefix
        }
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    
    @Override
    public Optional<StudentRegistration> findByEmail(String email) {
        return employeRepository.findByEmail(email);
    }
}
