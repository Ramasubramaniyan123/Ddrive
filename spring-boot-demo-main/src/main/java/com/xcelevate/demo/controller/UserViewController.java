package com.xcelevate.demo.controller;

import com.xcelevate.demo.service.DepartmentService;
import com.xcelevate.demo.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    private final UserService userService;
    private final DepartmentService departmentService;

    public UserViewController(UserService userService,
                              DepartmentService departmentService) {

        this.userService = userService;
        this.departmentService = departmentService;
    }

    @GetMapping("/")
    public String homePage(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );

        return "index";
    }

    @GetMapping("/users")
    public String usersPage(Model model) {

        model.addAttribute(
                "users",
                userService.getAllUsers()
        );

        model.addAttribute(
                "departments",
                departmentService.getAllDepartments()
        );

        return "index";
    }
}