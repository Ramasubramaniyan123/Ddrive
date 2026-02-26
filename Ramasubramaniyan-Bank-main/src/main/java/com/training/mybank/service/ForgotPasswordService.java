package com.training.mybank.service;

import com.training.mybank.entity.AccountEntity;
import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.InvalidRecoveryDetailsException;
import com.training.mybank.repository.AccountRepository;
import com.training.mybank.repository.UserRepository;
import com.training.mybank.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ForgotPasswordService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public ForgotPasswordService(UserRepository userRepository,
            AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    /* ---------- VERIFY RECOVERY DETAILS ---------- */

    private UserEntity verifyRecoveryDetails(String username,
            String email,
            String accountNumber) {

        UserEntity user = userRepository.findByUsernameAndEmail(username, email);

        AccountEntity account = accountRepository.findByUsernameAndAccountNumber(
                username, accountNumber);


        if (account.getIsFrozen()) {
            throw new InvalidRecoveryDetailsException(
                    "Account is frozen. Password reset not allowed");
        }

        return user;
    }

    /* ---------- RESET PASSWORD ---------- */

    @Transactional
    public void resetPassword(String username,
            String email,
            String accountNumber,
            String newPassword,
            String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new InvalidRecoveryDetailsException("Passwords do not match");
        }

        try {
            PasswordUtil.validateStrength(newPassword);
        } catch (IllegalArgumentException e) {
            throw new InvalidRecoveryDetailsException(e.getMessage());
        }

        try {
            UserEntity user = verifyRecoveryDetails(
                    username, email, accountNumber);

            user.setPassword(PasswordUtil.hash(newPassword));

        } catch (InvalidRecoveryDetailsException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidRecoveryDetailsException(
                    "Unable to reset password. Please verify your details and try again.");
        }
    }

}
