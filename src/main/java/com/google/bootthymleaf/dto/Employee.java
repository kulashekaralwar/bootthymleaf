package com.google.bootthymleaf.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    @Column(nullable = false)
    private String role;

    @ElementCollection
    private Set<String> skills;

    private String selectedSkill;
    private boolean quizPassed;
}

