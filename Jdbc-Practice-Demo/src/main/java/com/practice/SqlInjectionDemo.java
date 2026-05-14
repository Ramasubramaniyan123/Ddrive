package com.practice;

import java.sql.*;
import java.util.Scanner;

public class SqlInjectionDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter employee name:");
        String empName = sc.nextLine();

        try {

            Connection connection =
                    DriverManager.getConnection(url, user, password);

            Statement statement =
                    connection.createStatement();

            // VULNERABLE QUERY
            String query =
                    "select * from employee where name = '" + empName + "'";

            System.out.println("Executing Query:");
            System.out.println(query);

            ResultSet rs =
                    statement.executeQuery(query);

            while(rs.next()) {

                System.out.println(
                        rs.getInt("emp_id") + " " +
                                rs.getString("name") + " " +
                                rs.getDouble("salary")
                );
            }

            rs.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}