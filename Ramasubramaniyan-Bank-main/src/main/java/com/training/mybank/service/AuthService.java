package com.training.mybank.service;

import com.training.mybank.repository.UserRepository;
import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.AccountInactiveException;
import com.training.mybank.exception.AuthenticationFailedException;
import com.training.mybank.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Autowired
    public AuthService(UserRepository userRepository, AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    /* ---------- LOGIN ---------- */

    @Transactional(readOnly = true)
    public UserEntity login(String username, String password) {

        UserEntity user = userRepository.findOptionalByUsername(username);

        if (user == null) {
            auditLogService.log(username, "LOGIN_FAILED", "Username not found");
            throw new AuthenticationFailedException("Invalid username: " + username);
            
        }

        if (!PasswordUtil.matches(password, user.getPassword())) {
            auditLogService.log(username, "LOGIN_FAILED", "Invalid password");
            throw new AuthenticationFailedException("Invalid password for username: " + username);
        }


        auditLogService.log(username, "LOGIN_SUCCESS", "User logged in successfully");
        return user;
    }

    /* ---------- LOGOUT ---------- */

    public void logout(String username) {
        auditLogService.log(username, "LOGOUT", "User logged out");
        System.out.println("User " + username + " logged out.");
    }

    @Transactional(readOnly = true)
    public void verifyUserExists(String username) {
        if (!userRepository.existsByUsername(username)) {
            throw new AuthenticationFailedException("Invalid username: " + username);
        }
    }
}
