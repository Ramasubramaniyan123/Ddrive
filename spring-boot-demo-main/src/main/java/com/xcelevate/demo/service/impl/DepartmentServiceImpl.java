package com.xcelevate.demo.service.impl;

import com.xcelevate.demo.entity.Department;
import com.xcelevate.demo.model.request.DepartmentRequest;
import com.xcelevate.demo.model.response.DepartmentResponse;
import com.xcelevate.demo.repository.DepartmentRepository;
import com.xcelevate.demo.service.DepartmentService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .active(request.getActive() != null ? request.getActive() : true)
                .build();

        Department savedDepartment = departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    @Override
    public DepartmentResponse getDepartmentById(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        return mapToResponse(department);
    }

    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public DepartmentResponse updateDepartment(Long id, DepartmentRequest request) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setName(request.getName());
        department.setDescription(request.getDescription());
        department.setActive(request.getActive());

        Department updatedDepartment = departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    @Override
    public DepartmentResponse toggleDepartmentStatus(Long id) {

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        department.setActive(!Boolean.TRUE.equals(department.getActive()));

        Department updatedDepartment = departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    @Override
    public void deleteDepartment(Long id) {

        if (!departmentRepository.existsById(id)) {
            throw new RuntimeException("Department not found");
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public Page<DepartmentResponse> searchDepartments(String name, Pageable pageable) {

        return departmentRepository
                .findByNameContainingIgnoreCase(name, pageable)
                .map(this::mapToResponse);
    }

    private DepartmentResponse mapToResponse(Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .active(department.getActive())
                .build();
    }
}