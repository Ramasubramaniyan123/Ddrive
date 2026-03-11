package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeePreparedCRUD {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        try (Connection con = DriverManager.getConnection(url, user, password)) {


            // CREATE (INSERT)
            String insertSql =
                    "INSERT INTO employees(first_name,last_name,email,department,salary) VALUES(?,?,?,?,?)";

            PreparedStatement insertStmt = con.prepareStatement(insertSql);

            insertStmt.setString(1, "Alice");
            insertStmt.setString(2, "Johnson");
            insertStmt.setString(3, "alice@gmail.com");
            insertStmt.setString(4, "IT");
            insertStmt.setDouble(5, 65000);

            int inserted = insertStmt.executeUpdate();
            System.out.println(inserted + " employee inserted");

            // READ (SELECT)

            String selectSql = "SELECT * FROM employees";

            PreparedStatement selectStmt = con.prepareStatement(selectSql);
            ResultSet rs = selectStmt.executeQuery();

            System.out.println("\nEmployees List");
            System.out.println("------------------------------------------------");

            while (rs.next()) {

                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");

                System.out.printf("%d | %s | %s | %s | %s | %.2f\n",
                        id, firstName, lastName, email, department, salary);
            }


            // UPDATE

            String updateSql =
                    "UPDATE employees SET salary = ? WHERE first_name = ?";

            PreparedStatement updateStmt = con.prepareStatement(updateSql);

            updateStmt.setDouble(1, 70000);
            updateStmt.setString(2, "Alice");

            int updated = updateStmt.executeUpdate();
            System.out.println("\n" + updated + " employee updated");



            // DELETE
            String deleteSql =
                    "DELETE FROM employees WHERE first_name = ?";

            PreparedStatement deleteStmt = con.prepareStatement(deleteSql);

            deleteStmt.setString(1, "Alice");

            int deleted = deleteStmt.executeUpdate();
            System.out.println(deleted + " employee deleted");

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}