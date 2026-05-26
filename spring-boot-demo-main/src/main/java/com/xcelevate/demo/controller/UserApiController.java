package com.xcelevate.demo.controller;

import com.xcelevate.demo.model.request.UserRequest;
import com.xcelevate.demo.model.response.UserResponse;
import com.xcelevate.demo.service.UserService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    public UserApiController(UserService userService) {
        this.userService = userService;
    }

    // Create User
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.createUser(request));
    }

    // Get User By Id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get All Users
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest request) {

        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    // Toggle User Status
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<UserResponse> toggleUserStatus(
            @PathVariable Long id) {

        return ResponseEntity.ok(userService.toggleUserStatus(id));
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    // Search Users + Pagination
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponse>> searchUsers(
            @RequestParam(defaultValue = "") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Pageable pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(
                userService.searchUsers(name, pageable)
        );
    }
}