package com.xcelevate.demo.service;

import com.xcelevate.demo.model.request.UserRequest;
import com.xcelevate.demo.model.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUserById(Long id);
    List<UserResponse> getAllUsers();
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse toggleUserStatus(Long id);
    void deleteUser(Long id);
    Page<UserResponse> searchUsers(String name, Pageable pageable);
}
