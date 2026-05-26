package com.xcelevate.demo.repository;

import com.xcelevate.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<User> findByRole(String role);

    List<User> findByActive(Boolean active);

    List<User> findByDepartmentId(Long departmentId);

    List<User> findByNameContainingIgnoreCase(String name);

    Page<User> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByEmail(String email);
}