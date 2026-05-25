package com.xcelevate.demo.controller;

import com.xcelevate.demo.entity.Department;
import com.xcelevate.demo.model.request.DepartmentRequest;
import com.xcelevate.demo.model.response.DepartmentResponse;
import com.xcelevate.demo.repository.DepartmentRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/departments")
public class DepartmentApiController {

    @Autowired
    private DepartmentRepository departmentRepository;

    private DepartmentResponse convertToResponse(Department department) {
        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .active(department.getActive())
                .build();
    }

    // READ: Get all departments (GET)
    @GetMapping
    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    // READ: Get department by ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponse> getDepartmentById(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(dept -> ResponseEntity.ok(convertToResponse(dept)))
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE: Create new department (POST)
    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody DepartmentRequest departmentRequest) {
        Department department = Department.builder()
                .name(departmentRequest.getName())
                .description(departmentRequest.getDescription())
                .active(departmentRequest.getActive() == null || Boolean.TRUE.equals(departmentRequest.getActive()))
                .build();

        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToResponse(savedDepartment));
    }

    // UPDATE: Full update (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @Valid @RequestBody DepartmentRequest departmentDetails) {
        return departmentRepository.findById(id)
                .map(dept -> {
                    dept.setName(departmentDetails.getName());
                    dept.setDescription(departmentDetails.getDescription());
                    if (departmentDetails.getActive() != null) {
                        dept.setActive(departmentDetails.getActive());
                    }
                    Department updatedDept = departmentRepository.save(dept);
                    return ResponseEntity.ok((Object) convertToResponse(updatedDept));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PARTIAL UPDATE: Toggle active status (PATCH)
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<DepartmentResponse> toggleDepartmentStatus(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(dept -> {
                    dept.setActive(!Boolean.TRUE.equals(dept.getActive()));
                    Department updatedDept = departmentRepository.save(dept);
                    return ResponseEntity.ok(convertToResponse(updatedDept));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Delete department (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
