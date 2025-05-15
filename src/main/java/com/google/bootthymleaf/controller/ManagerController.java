package com.google.bootthymleaf.controller;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.repository.EmployeeRepository;
import com.google.bootthymleaf.service.AIService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private AIService aiService;

    // Manager Dashboard – List all employees
    @GetMapping("/dashboard")
    public String managerDashboard(Model model) {
        List<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "manager-dashboard";
    }

    // View an individual employee's profile
    @GetMapping("/view/{id}")
    public String viewEmployee(@PathVariable("id") int id, Model model) {
        Employee emp = employeeRepo.findById(id).orElse(null);
        model.addAttribute("employee", emp);
        model.addAttribute("skills", aiService.getAvailableSkills());
        return "manager-view-employee";
    }

    // Assign roadmap manually (optional, for future use)
    @PostMapping("/assign-roadmap")
    public String assignRoadmap(@RequestParam("empId") int empId,
                                @RequestParam("skill") String skill,
                                Model model) {
        Employee emp = employeeRepo.findById(empId).orElse(null);
        if (emp != null) {
            Map<String, Object> roadmap = aiService.generateRoadmap(emp, skill);
            model.addAttribute("employee", emp);
            model.addAttribute("roadmap", roadmap);
            model.addAttribute("selectedSkill", skill);
        }
        return "manager-view-employee";
    }
}
