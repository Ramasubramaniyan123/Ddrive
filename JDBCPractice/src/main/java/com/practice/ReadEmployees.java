package com.practice;

import java.sql.*;

public class ReadEmployees {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";
        String query = "select * from employees";
        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);) {
            System.out.println("Employees Table");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String first_name = resultSet.getString("first_name");
                String last_name = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String department = resultSet.getString("department");
                double salary = resultSet.getDouble("salary");
                //System.out.println(id + " " + first_name + " | " + last_name + " | " + email + " | " + department+ " |  " + salary);
                System.out.printf("%d | %s | %s | %s | %s | %.2f\n", id, first_name, last_name, email, department, salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
