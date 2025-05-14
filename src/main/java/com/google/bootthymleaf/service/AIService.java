package com.google.bootthymleaf.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AIService {

    public String generateRoadmap(String selectedSkill, Set<String> currentSkills) {
        // Simulate AI logic
        StringBuilder roadmap = new StringBuilder();
        roadmap.append("Roadmap to learn ").append(selectedSkill).append(":\n");
        roadmap.append("1. Understand the basics\n");
        roadmap.append("2. Practice small projects\n");
        roadmap.append("3. Refer official docs\n");
        roadmap.append("4. Take the skill quiz\n");

        return roadmap.toString();
    }
}
