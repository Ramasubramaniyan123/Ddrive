package com.practice;

import java.sql.*;

public class EmployeeCRUD {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement statement = conn.createStatement();) {

            //Insert
            String insert = "insert into employees(first_name,last_name,email,department,salary)" +
                    " values ('Ram','K','ram@gmail.com','IT',65000.00)";
            statement.executeUpdate(insert);
            System.out.println("Employee Inserted");


            //READ
            String select = "select * from employees";
            ResultSet resultSet = statement.executeQuery(select);
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


            //UPDATE

            String update = "update employees set salary = 70000 where first_name = 'John'";
            int updated = statement.executeUpdate(update);
            System.out.println("Number of rows updated:  " + updated);

            //DELETE

            String delete = "delete from employees where first_name = 'Jane'";
            int deleted = statement.executeUpdate(delete);
            System.out.println("Number of rows deleted: " + deleted);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
