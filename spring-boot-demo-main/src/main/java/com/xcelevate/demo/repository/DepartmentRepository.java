package com.xcelevate.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xcelevate.demo.entity.Department;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByName(String name);

    List<Department> findByActive(boolean active);

    boolean existsByName(String name);

    List<Department> findNameContainingIgnoreCase(String name);

    Page<Department> findNameContainingIgnoreCase(String name, Pageable pageable);
}
