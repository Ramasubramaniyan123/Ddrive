package com.training.mybank.service;

import com.training.mybank.entity.User;
import com.training.mybank.exception.BankingException;
import com.training.mybank.repository.UserRepository;
import com.training.mybank.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    public UserService(UserRepository userRepository, AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.trim().isEmpty()) {
            throw new BankingException("Old password cannot be empty");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new BankingException("New password cannot be empty");
        }
        if (newPassword.length() < 4) {
            throw new BankingException("New password must be at least 4 characters long");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BankingException("User not found with username: " + username));

        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            auditLogService.log(username, "PASSWORD_CHANGE_FAILED", "Incorrect old password");
            throw new BankingException("Incorrect old password");
        }
        if (PasswordUtil.matches(newPassword, user.getPassword())) {
            throw new BankingException("New password must be different from the current password");
        }

        user.setPassword(PasswordUtil.hash(newPassword));
        userRepository.save(user);
    }

    @Transactional
    public void updateProfile(String username, String fullName, String email) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BankingException("User not found with username: " + username));

        if (fullName != null && !fullName.trim().isEmpty()) {
            user.setFullName(fullName);
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(email).matches()) {
                throw new BankingException("Invalid email format");
            }
            if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new BankingException("Email already registered: " + email);
            }
            user.setEmail(email);
        }

        userRepository.save(user);
        auditLogService.log(username, "PROFILE_UPDATED", "User updated their profile information");
    }

    @Transactional(readOnly = true)
    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BankingException("User not found with username: " + username));
    }
}
