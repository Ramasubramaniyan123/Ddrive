package com.service;


import com.dao.DepartmentDAO;
import com.model.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDAO departmentDAO = new DepartmentDAO();

    public void createDepartment(Department department) {
        departmentDAO.save(department);
    }

    public Department getDepartment(Long id) {
        return departmentDAO.findById(id);
    }

    public List<Department> getAllDepartments() {
        return departmentDAO.findAll();
    }

    public void updateDepartment(Department department) {
        departmentDAO.update(department);
    }

    public void deleteDepartment(Long id) {
        departmentDAO.delete(id);
    }
}