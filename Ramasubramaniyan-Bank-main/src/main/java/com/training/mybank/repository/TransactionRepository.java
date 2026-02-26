package com.training.mybank.repository;

import com.training.mybank.entity.TransactionEntity;
import com.training.mybank.entity.UserEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    @PersistenceContext
    private EntityManager em;

    /* -------- SAVE -------- */

    public void save(TransactionEntity tx) {
        em.persist(tx);
    }

    /* -------- ACCOUNT TRANSACTIONS (USER) -------- */

    public List<TransactionEntity> findByAccountId(Long accountId) {
        return em.createQuery(
                        "SELECT t FROM TransactionEntity t " +
                                "LEFT JOIN t.fromAccount f " +
                                "LEFT JOIN t.toAccount toA " +
                                "WHERE f.id = :id " +
                                "   OR toA.id = :id " +
                                "ORDER BY t.createdAt DESC",
                        TransactionEntity.class).setParameter("id", accountId)
                .getResultList();
    }
}
