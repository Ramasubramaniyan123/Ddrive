package com.training.mybank.service;

import com.training.mybank.entity.Role;
import com.training.mybank.repository.AccountRepository;
import com.training.mybank.repository.UserRepository;
import com.training.mybank.entity.AccountEntity;
import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.BankingException;
import com.training.mybank.util.PasswordUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.regex.Pattern;

@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    @Autowired
    public RegistrationService(UserRepository userRepository,
            AccountRepository accountRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public String register(String username,
            String password,
            String fullName,
            String email) {

        validateInput(username, password, email);

        // Create user
        UserEntity user = new UserEntity();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hash(password));
        user.setFullName(fullName);
        user.setEmail(email);
        user.setRole(Role.USER);

        userRepository.save(user);

        // Create account
        String accountNumber = accountRepository.generateUniqueAccountNumber();
        AccountEntity account = new AccountEntity();
        account.setUsername(username);
        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        accountRepository.save(account);

        return accountNumber;
    }

    private void validateInput(String username, String password, String email) {
        if (username == null || username.trim().isEmpty()) {
            throw new BankingException("Username cannot be empty");
        }
        if ("admin".equalsIgnoreCase(username) || "system".equalsIgnoreCase(username)) {
            throw new BankingException("Username '" + username + "' is reserved");
        }
        if (userRepository.existsByUsername(username)) {
            throw new BankingException("Username already exists");
        }
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new BankingException("Invalid email format");
        }
        if (userRepository.existsByEmail(email)) {
            throw new BankingException("Email already registered");
        }
        try {
            PasswordUtil.validateStrength(password);
        } catch (IllegalArgumentException e) {
            throw new BankingException(e.getMessage());
        }
    }

    /* ---------- UI HELPER ---------- */

    public String registerUser(String username, String password, String fullName, String email) throws BankingException {
        return register(username, password, fullName, email);
    }
}
