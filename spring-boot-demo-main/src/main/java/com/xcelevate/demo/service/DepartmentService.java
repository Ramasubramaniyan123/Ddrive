package com.xcelevate.demo.service;

import com.xcelevate.demo.model.request.DepartmentRequest;
import com.xcelevate.demo.model.response.DepartmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DepartmentService {
    DepartmentResponse createDepartment(DepartmentRequest resuest);

    DepartmentResponse getDepartmentById(Long id);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Long id, DepartmentRequest request);

    DepartmentResponse toggleDepartmentStatus(Long id);

    void deleteDepartment(Long id);

    Page<DepartmentResponse> searchDepartments(String name, Pageable pageable);

}
