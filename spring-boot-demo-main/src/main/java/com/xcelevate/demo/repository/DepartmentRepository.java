package com.xcelevate.demo.repository;

import com.xcelevate.demo.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    List<Department> findByActive(Boolean active);

    List<Department> findByNameContainingIgnoreCase(String name);

    Page<Department> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByName(String name);
}