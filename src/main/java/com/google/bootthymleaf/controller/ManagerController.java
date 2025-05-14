package com.google.bootthymleaf.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.repository.EmployeeRepository;

import java.util.List;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Object manager = session.getAttribute("manager");
        if (manager == null) return "redirect:/login";

        List<Employee> employees = employeeRepo.findAll();
        model.addAttribute("employees", employees);
        return "manager-dashboard";
    }
}
