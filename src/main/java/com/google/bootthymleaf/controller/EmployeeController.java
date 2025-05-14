package com.google.bootthymleaf.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.repository.EmployeeRepository;
import com.google.bootthymleaf.service.AIService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AIService aiService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) return "redirect:/login";

        model.addAttribute("employee", employee);
        return "employee-dashboard";
    }

    @PostMapping("/select-skill")
    public String selectSkill(@RequestParam String skill, HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) return "redirect:/login";

        employee.setSelectedSkill(skill);
        employeeRepo.save(employee);

        String roadmap = aiService.generateRoadmap(skill, employee.getSkills());
        model.addAttribute("roadmap", roadmap);
        model.addAttribute("employee", employee);
        return "employee-roadmap";
    }

    @PostMapping("/submit-quiz")
    public String submitQuiz(@RequestParam boolean passed, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) return "redirect:/login";

        if (passed) {
            employee.getSkills().add(employee.getSelectedSkill());
            employee.setQuizPassed(true);
        } else {
            employee.setQuizPassed(false);
        }

        employeeRepo.save(employee);
        return "redirect:/employee/dashboard";
    }
}
