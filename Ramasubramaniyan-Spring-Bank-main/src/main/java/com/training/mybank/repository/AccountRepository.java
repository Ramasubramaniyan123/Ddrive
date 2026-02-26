package com.training.mybank.repository;

import com.training.mybank.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Random;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

    Random RANDOM = new Random();

    boolean existsByAccountNumber(String accountNumber);

    Optional<AccountEntity> findByUsername(String username);

    Optional<AccountEntity> findByUsernameAndAccountNumber(String username, String accountNumber);

    default String generateUniqueAccountNumber() {
        int min = 10000000;
        int max = 99999999;
        String accountNumber;

        while (true) {
            int number = min + RANDOM.nextInt(max - min + 1);
            accountNumber = String.valueOf(number);

            if (!existsByAccountNumber(accountNumber)) {
                break;
            }
        }
        return accountNumber;
    }
}
