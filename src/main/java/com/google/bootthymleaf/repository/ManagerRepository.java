package com.google.bootthymleaf.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.google.bootthymleaf.dto.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByUsername(String username);
}
