package com.google.bootthymleaf.service;

import com.google.bootthymleaf.dto.Employee;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AIService {

    // Sample AI skill library (can be moved to DB or config)
    private final Map<String, List<String>> skillRoadmaps = Map.of(
        "Java", List.of("Core Java", "OOP", "Collections", "JDBC", "Multithreading"),
        "Spring Boot", List.of("Spring Core", "Spring MVC", "Spring Data JPA", "Security", "REST APIs"),
        "AWS", List.of("EC2", "S3", "IAM", "Lambda", "VPC", "CloudWatch")
    );

    private final Map<String, List<String>> skillResources = Map.of(
        "Java", List.of("Java Tutorials - Oracle", "Java Programming on Udemy", "GeeksforGeeks Java"),
        "Spring Boot", List.of("Spring Docs", "Baeldung Spring Boot", "Spring Boot on Coursera"),
        "AWS", List.of("AWS Skill Builder", "FreeCodeCamp AWS", "AWS Docs")
    );

    // Return available skills to pick from
    public List<String> getAvailableSkills() {
        return new ArrayList<>(skillRoadmaps.keySet());
    }

    // Main method: generates roadmap
    public Map<String, Object> generateRoadmap(Employee emp, String skill) {
        Map<String, Object> roadmapResult = new HashMap<>();

        List<String> fullRoadmap = skillRoadmaps.getOrDefault(skill, new ArrayList<>());
        List<String> employeeSkills = emp.getSkills() != null ? emp.getSkills() : new ArrayList<>();

        // Identify gaps
        List<String> missing = new ArrayList<>();
        for (String step : fullRoadmap) {
            if (!employeeSkills.contains(step)) {
                missing.add(step);
            }
        }

        roadmapResult.put("skill", skill);
        roadmapResult.put("requiredSteps", fullRoadmap);
        roadmapResult.put("missingSteps", missing);
        roadmapResult.put("resources", skillResources.getOrDefault(skill, List.of("No resources found")));

        return roadmapResult;
    }
}
