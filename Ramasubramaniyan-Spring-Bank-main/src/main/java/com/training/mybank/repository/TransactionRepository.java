package com.training.mybank.repository;

import com.training.mybank.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

    @Query("SELECT t FROM TransactionEntity t " +
            "LEFT JOIN t.fromAccount f " +
            "LEFT JOIN t.toAccount toA " +
            "WHERE f.id = :id OR toA.id = :id " +
            "ORDER BY t.createdAt DESC")
    List<TransactionEntity> findByAccountId(@Param("id") Long accountId);
}
