package com.training.mybank.repository;

import com.training.mybank.entity.UserEntity;
import com.training.mybank.exception.BankingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @PersistenceContext
    private EntityManager em;

    /* ---------- READ OPERATIONS ---------- */

    public boolean existsByUsername(String username) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM UserEntity u WHERE u.username = :username",
                        Long.class).setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    public boolean existsByEmail(String email) {
        Long count = em.createQuery(
                        "SELECT COUNT(u) FROM UserEntity u WHERE u.email = :email",
                        Long.class).setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    // STRICT – throws exception
    public UserEntity findByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.username = :username",
                            UserEntity.class).setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BankingException(
                    "User not found with username: " + username);
        }
    }

    // SAFE – returns null
    public UserEntity findOptionalByUsername(String username) {
        try {
            return em.createQuery(
                            "SELECT u FROM UserEntity u WHERE u.username = :username",
                            UserEntity.class).setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public UserEntity findByUsernameAndEmail(String username, String email) {

        try {
            return em.createQuery(
                            "SELECT u FROM UserEntity u " +
                                    "WHERE u.username = :u AND u.email = :e",
                            UserEntity.class).setParameter("u", username)
                    .setParameter("e", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new BankingException(
                    "User not found with username: " + username +
                            " and email: " + email);
        }
    }

    public String getPasswordByUsername(String username){
       try{
           UserEntity user=findByUsername(username);
           return user.getPassword();
       }
       catch (NoResultException e){
           throw new BankingException("Username is not valid");
       }
    }

    /* ---------- WRITE OPERATIONS ---------- */

    public void save(UserEntity user) {
        em.persist(user);
    }


    public void update(UserEntity user) {
        em.merge(user);
    }

}
