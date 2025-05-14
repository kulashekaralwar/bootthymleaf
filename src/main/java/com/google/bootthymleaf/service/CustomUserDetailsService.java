package com.google.bootthymleaf.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.google.bootthymleaf.dto.Employee;
import com.google.bootthymleaf.dto.Manager;
import com.google.bootthymleaf.repository.EmployeeRepository;
import com.google.bootthymleaf.repository.ManagerRepository;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepo;

    @Autowired
    private ManagerRepository managerRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee emp = employeeRepo.findByUsername(username);
        if (emp != null) {
            return new User(
                    emp.getUsername(),
                    emp.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))
            );
        }

        Manager mgr = managerRepo.findByUsername(username);
        if (mgr != null) {
            return new User(
                    mgr.getUsername(),
                    mgr.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_MANAGER"))
            );
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
