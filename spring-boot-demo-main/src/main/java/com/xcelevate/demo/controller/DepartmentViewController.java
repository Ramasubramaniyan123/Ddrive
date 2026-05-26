package com.xcelevate.demo.controller;

import com.xcelevate.demo.service.DepartmentService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DepartmentViewController {

    private final DepartmentService departmentService;

    public DepartmentViewController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public String departmentsPage(Model model) {

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );

        return "departments";
    }
}