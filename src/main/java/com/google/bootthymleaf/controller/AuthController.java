package com.google.bootthymleaf.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.dto.Manager;
import com.google.bootthymleaf.repository.EmployeeRepository;
import com.google.bootthymleaf.repository.ManagerRepository;

@Controller
public class AuthController {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        Employee employee = employeeRepo.findByEmail(email);
        Manager manager = managerRepo.findByUsername(email);

        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
            session.setAttribute("employee", employee);
            return "redirect:/employee/dashboard";
        } else if (manager != null && passwordEncoder.matches(password, manager.getPassword())) {
            session.setAttribute("manager", manager);
            return "redirect:/manager/dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials!");
            return "login";
        }
    }

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@RequestParam String username,
                         @RequestParam String password,
                         @RequestParam String role,
                         Model model) {
        if (role.equalsIgnoreCase("EMPLOYEE")) {
            Employee emp = new Employee();
            emp.setUsername(username);
            emp.setEmail(username); // Assuming email is used as username
            emp.setPassword(passwordEncoder.encode(password));
            emp.setRole("EMPLOYEE");
            employeeRepo.save(emp);
        } else if (role.equalsIgnoreCase("MANAGER")) {
            Manager mgr = new Manager();
            mgr.setUsername(username);
            mgr.setPassword(passwordEncoder.encode(password));
            mgr.setRole("MANAGER");
            managerRepo.save(mgr);
        } else {
            model.addAttribute("error", "Invalid role selected");
            return "signup";
        }

        return "redirect:/";
    }
}
