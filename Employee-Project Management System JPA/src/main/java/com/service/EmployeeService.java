package com.service;


import com.dao.EmployeeDAO;
import com.model.Employee;

import java.util.List;

public class EmployeeService {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void createEmployee(Employee employee) {
        employeeDAO.save(employee);
    }

    public Employee getEmployee(Long id) {
        return employeeDAO.findById(id);
    }

    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    public void updateEmployee(Employee employee) {
        employeeDAO.update(employee);
    }

    public void deleteEmployee(Long id) {
        employeeDAO.delete(id);
    }
}
