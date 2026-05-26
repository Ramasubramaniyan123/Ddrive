package com.xcelevate.demo.service.impl;

import com.xcelevate.demo.entity.Department;
import com.xcelevate.demo.entity.User;
import com.xcelevate.demo.model.request.UserRequest;
import com.xcelevate.demo.model.response.UserResponse;
import com.xcelevate.demo.repository.DepartmentRepository;
import com.xcelevate.demo.repository.UserRepository;
import com.xcelevate.demo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;

    public UserServiceImpl(DepartmentRepository departmentRepository, UserRepository userRepository) {
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
    }


    @Override
    public UserResponse createUser(UserRequest request) {
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found! "));

        User user = User.builder()
                .name(request.getName())
                .email(request.getName())
                .role(request.getRole())
                .active(request.getActive() != null ? request.getActive() : true)
                .department(department)
                .build();

        User savedUser = userRepository.save(user);

        return mapToResponse(savedUser);
    }

    private UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .active(user.getActive())
                .departmentId(user.getDepartment().getId())
                .departmentName(user.getDepartment().getName())
                .build();
    }

    @Override
    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setActive(request.getActive());
        user.setDepartment(department);

        User updatedUSer = userRepository.save(user);
        return mapToResponse(updatedUSer);
    }

    @Override
    public UserResponse toggleUserStatus(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(!Boolean.TRUE.equals(user.getActive()));
        User updatedUser = userRepository.save(user);
        return mapToResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        if(!userRepository.existsById(id)){
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Page<UserResponse> searchUsers(String name, Pageable pageable) {
       return userRepository.findByNameContainingIgnoreCase(name, pageable)
               .map(this::mapToResponse);
    }
}
