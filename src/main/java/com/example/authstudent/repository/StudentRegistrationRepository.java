package com.example.authstudent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.authstudent.entity.StudentRegistration;

import java.util.Optional;

@Repository
public interface StudentRegistrationRepository extends JpaRepository<StudentRegistration, Long> {
    Optional<StudentRegistration> findByEmail(String email);
}
