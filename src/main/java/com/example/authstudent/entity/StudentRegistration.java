package com.example.authstudent.entity;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "student_table")
public class StudentRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "college_name", nullable = false)
    private String collegeName;


    @Column(name = "degree", nullable = false)
    private String degree;

    @Column(name = "year_of_study", nullable = false)
    private int yearOfStudy;

    @Column(name = "city", nullable = false)
    private String city;


    @Column(name = "gender")
    private String gender;


    public StudentRegistration() {}

    public StudentRegistration(Long id, String name, String email, String password, String phoneNumber, String collegeName,  String degree, int yearOfStudy, String city, String gender) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.collegeName = collegeName;
        this.degree = degree;
        this.yearOfStudy = yearOfStudy;
        this.city = city;
        this.gender = gender;
    }

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getCollegeName() { return collegeName; }
    public void setCollegeName(String collegeName) { this.collegeName = collegeName; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

}
