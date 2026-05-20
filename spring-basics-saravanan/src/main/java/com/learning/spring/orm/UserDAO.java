package com.learning.spring.orm;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class UserDAO {

    // Spring injects the EntityManager automatically
    @PersistenceContext
    private EntityManager entityManager;

    // Transactional ensures a transaction is opened and closed/committed automatically
    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    public List<User> findAll() {
        // JPQL (Java Persistence Query Language) - query objects, not tables
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
}