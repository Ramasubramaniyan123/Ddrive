package com.training.mybank.service;

import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.BankingException;
import com.training.mybank.repository.UserRepository;
import com.training.mybank.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuditLogService auditLogService;

    @Autowired
    public UserService(UserRepository userRepository, AuditLogService auditLogService) {
        this.userRepository = userRepository;
        this.auditLogService = auditLogService;
    }

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {
        UserEntity user = userRepository.findByUsername(username);
        if(newPassword.equals(userRepository.getPasswordByUsername(username))){
            auditLogService.log((username), "SAME_PASSWORD_ENTERED", "Why you came here to change the password");
            throw new BankingException("You should not enter the same password to change");
        }

        if (!PasswordUtil.matches(oldPassword, user.getPassword())) {
            auditLogService.log(username, "PASSWORD_CHANGE_FAILED", "Incorrect old password");
            throw new BankingException("Incorrect old password");
        }

        PasswordUtil.validateStrength(newPassword);
        user.setPassword(PasswordUtil.hash(newPassword));
        userRepository.update(user);
        

        
    }
    @Transactional
    public void updateProfile(String username, String fullName, String email) {
        UserEntity user = userRepository.findByUsername(username);

        if (fullName != null && !fullName.trim().isEmpty()) {
            user.setFullName(fullName);
        }

        if (email != null && !email.trim().isEmpty()) {
            if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new BankingException("Email already registered: " + email);
            }
            user.setEmail(email);
        }

        userRepository.update(user);
        auditLogService.log(username, "PROFILE_UPDATED", "User updated their profile information");
    }

    @Transactional(readOnly = true)
    public UserEntity getProfile(String username) {
        return userRepository.findByUsername(username);
    }
}
