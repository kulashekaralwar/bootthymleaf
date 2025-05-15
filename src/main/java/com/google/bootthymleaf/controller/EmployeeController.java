package com.google.bootthymleaf.controller;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.repository.EmployeeRepository;
import com.google.bootthymleaf.service.AIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AIService aiService;

    // Dashboard
    @GetMapping("/dashboard")
    public String employeeDashboard(Model model, Principal principal) {
        Employee emp = employeeRepo.findByEmail(principal.getName());
        model.addAttribute("employee", emp);
        return "employee-dashboard";
    }

    // Skill selection page
    @GetMapping("/roadmap")
    public String showSkillSelection(Model model) {
        List<String> skills = aiService.getAvailableSkills(); // e.g., ["Java", "AWS", "Spring"]
        model.addAttribute("skills", skills);
        return "employee-roadmap";
    }

    // Handle skill selection and generate roadmap
    @PostMapping("/generate-roadmap")
    public String generateRoadmap(@RequestParam("selectedSkill") String skill, Principal principal, Model model) {
        Employee emp = employeeRepo.findByEmail(principal.getName());

        Map<String, Object> roadmap = aiService.generateRoadmap(emp, skill);
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("selectedSkill", skill);
        return "employee-roadmap";
    }

    // Quiz evaluation (mock logic)
    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam("skill") String skill, @RequestParam("score") int score, Principal principal, Model model) {
        Employee emp = employeeRepo.findByEmail(principal.getName());

        if (score >= 70) {
            // Add skill to employee profile
            emp.getSkills().add(skill);
            employeeRepo.save(emp);
            model.addAttribute("message", "Quiz passed! Skill added.");
        } else {
            model.addAttribute("message", "Quiz failed. Please try again.");
        }

        model.addAttribute("employee", emp);
        return "employee-dashboard";
    }
}
