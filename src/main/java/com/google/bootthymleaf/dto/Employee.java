package com.google.bootthymleaf.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String email;

    private String password;

    // Store selected skills as a list of strings in a separate table
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "employee_skills", joinColumns = @JoinColumn(name = "employee_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();

    // Optional field to track skill currently being upskilled
    private String currentLearningSkill;

    // Optional field to store quiz status
    private boolean quizPassed;

    // Future extensibility
    private String role; // EMPLOYEE or MANAGER
}
