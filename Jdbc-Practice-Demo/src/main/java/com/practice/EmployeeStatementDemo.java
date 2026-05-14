package com.practice;

import java.sql.*;

public class EmployeeStatementDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            //Select
            String selectQuery = "select emp_id, name, salary from employee";
            ResultSet resultset = statement.executeQuery(selectQuery);
            while (resultset.next()) {
                int id = resultset.getInt("emp_id");
                String name = resultset.getString("name");
                double salary = resultset.getDouble("salary");
                System.out.println(id + " " + name + " " + salary);
            }

           // Insert
            String insertQuery = "insert into employee (emp_id, name, salary) values (111, 'Vettrivel Nallasivan',65000)";
            int updates = statement.executeUpdate(insertQuery);
            System.out.println("Inserted " + updates + " records into employee table");

            //update

            String updateSql = "update employee set salary = 100000 where emp_id = 103";
            statement.executeUpdate(updateSql);
            System.out.println("Employee updated successfully");

            //delete
            String deletesql = "delete from employee where emp_id = 111";
            int num = statement.executeUpdate(deletesql);
            System.out.println(num);
            System.out.println("Employee deleted successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
