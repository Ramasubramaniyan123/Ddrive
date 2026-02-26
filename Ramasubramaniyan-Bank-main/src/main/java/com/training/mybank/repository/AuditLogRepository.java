package com.training.mybank.repository;

import com.training.mybank.entity.AuditLogEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AuditLogRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(AuditLogEntity log) {
        em.persist(log);
    }

}
