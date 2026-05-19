package com.learning.spring.jdbc;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {

    private JdbcTemplate jdbcTemplate;

    // Spring injects the DataSource here
    public UserDAO(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Method to create table and data (just for this demo)
    public void initDb() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("CREATE TABLE users (user_id VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
        jdbcTemplate.update("INSERT INTO users (user_id, password) VALUES (?, ?)", "john_doe", "secret123");
        jdbcTemplate.update("INSERT INTO users (user_id, password) VALUES (?, ?)", "jane_smith", "admin456");
    }

    // Method to Read All Users
    public List<User> getAllUsers() {
        String sql = "SELECT user_id, password FROM users";

        // RowMapper converts a JDBC ResultSet row into a Java Object
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    // Inner class to map SQL rows to User objects
    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getString("user_id"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    }
}
