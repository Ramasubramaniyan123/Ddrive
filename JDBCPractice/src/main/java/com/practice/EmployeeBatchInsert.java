package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeBatchInsert {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        long startBatch = System.currentTimeMillis();
        batchInsertEmployees(url, user, password);
        long endBatch = System.currentTimeMillis();

        System.out.println("Batch Insert Time: " + (endBatch - startBatch) + " ms");

        long startIndividual = System.currentTimeMillis();
        individualInsertEmployees(url, user, password);
        long endIndividual = System.currentTimeMillis();

        System.out.println("Individual Insert Time: " + (endIndividual - startIndividual) + " ms");
    }

    // -------------------- BATCH INSERT --------------------

    public static void batchInsertEmployees(String url, String user, String password) {

        String sql = "INSERT INTO employee_batch (name, salary) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (int i = 1; i <= 5000; i++) {

                pstmt.setString(1, "Emp" + i);
                pstmt.setDouble(2, 1000 + i);

                pstmt.addBatch();
            }

            pstmt.executeBatch();

            con.commit();

            System.out.println("Batch insert completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // -------------------- INDIVIDUAL INSERT --------------------

    public static void individualInsertEmployees(String url, String user, String password) {

        String sql = "INSERT INTO employee_batch (name, salary) VALUES (?, ?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (int i = 1; i <= 5000; i++) {

                pstmt.setString(1, "SingleEmp" + i);
                pstmt.setDouble(2, 2000 + i);

                pstmt.executeUpdate();
            }

            System.out.println("Individual inserts completed.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}