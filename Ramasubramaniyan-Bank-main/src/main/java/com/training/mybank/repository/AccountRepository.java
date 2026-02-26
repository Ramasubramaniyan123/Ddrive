package com.training.mybank.repository;

import com.training.mybank.entity.AccountEntity;
import com.training.mybank.exception.BankingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Random;

@Repository
public class AccountRepository {

    private static final Random RANDOM = new Random();
    @PersistenceContext
    private EntityManager em;


    public boolean existsByAccountNumber(String accountNumber) {
        Long count = em.createQuery("SELECT COUNT(a) FROM AccountEntity a WHERE a.accountNumber = :acc", Long.class).setParameter("acc", accountNumber).getSingleResult();

        return count > 0;
    }

    public AccountEntity findByUsername(String username) {
        try {
            return em.createQuery("SELECT a FROM AccountEntity a WHERE a.username = :username", AccountEntity.class).setParameter("username", username).getSingleResult();
        } catch (NoResultException e) {
            throw new BankingException("Account not found for username: " + username);
        }
    }

    public AccountEntity findByUsernameAndAccountNumber(String username, String accountNumber) {

        try {
            return em.createQuery("SELECT a FROM AccountEntity a " + "WHERE a.username = :u AND a.accountNumber = :acc", AccountEntity.class).setParameter("u", username).setParameter("acc", accountNumber).getSingleResult();
        } catch (NoResultException e) {
            throw new BankingException("Account not found for username: " + username + " and account number: " + accountNumber);
        }
    }

    public List<AccountEntity> findAll() {
        return em.createQuery("SELECT a FROM AccountEntity a", AccountEntity.class).getResultList();
    }

    public String generateUniqueAccountNumber() {
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

    /* ---------- WRITE OPERATIONS ---------- */

    public void save(AccountEntity account) {
        em.persist(account);
    }

    public AccountEntity update(AccountEntity account) {
        return em.merge(account);
    }

}
