package com.learning.spring.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Type Arguments: <Entity Class, ID Type>
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // You can define custom queries just by naming methods!
    // Spring Data automatically implements this:
    User findByPassword(String password);

}
