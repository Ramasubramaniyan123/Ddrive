package com.xcelevate.demo.controller;

import com.xcelevate.demo.model.request.DepartmentRequest;
import com.xcelevate.demo.model.response.DepartmentResponse;
import com.xcelevate.demo.service.DepartmentService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentApiController {

    private final DepartmentService departmentService;

    public DepartmentApiController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    // Create Department
    @PostMapping
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.createDepartment(request)
        );
    }

    // Get Department By Id
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(id)
        );
    }

    // Get All Departments
    @GetMapping
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments()
        );
    }

    // Update Department
    @PutMapping("/{id}")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long id,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(id, request)
        );
    }

    // Toggle Department Status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<DepartmentResponse> toggleDepartmentStatus(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                departmentService.toggleDepartmentStatus(id)
        );
    }

    // Delete Department
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.noContent().build();
    }

    // Search Departments + Pagination
    @GetMapping("/search")
    public ResponseEntity<Page<DepartmentResponse>> searchDepartments(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                departmentService.searchDepartments(name, pageable)
        );
    }
}