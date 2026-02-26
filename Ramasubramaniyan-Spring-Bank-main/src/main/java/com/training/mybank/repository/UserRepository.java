package com.training.mybank.repository;

import com.training.mybank.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByUsernameAndEmail(String username, String email);

    default Optional<UserEntity> findOptionalByUsername(String username) {
        return findByUsername(username);
    }
}
