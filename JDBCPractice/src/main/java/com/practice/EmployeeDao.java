package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeDao {

    String url = "jdbc:mysql://localhost:3306/jdbc";
    String user = "root";
    String password = "Ram@2005";


    // CREATE
    public void addEmployee(String firstName, String lastName,
                            String email, String department, double salary) {

        String sql = "INSERT INTO employees(first_name,last_name,email,department,salary) VALUES(?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, email);
            ps.setString(4, department);
            ps.setDouble(5, salary);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee inserted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // READ
    public void listEmployees() {

        String sql = "SELECT * FROM employees";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nEmployees List");
            System.out.println("--------------------------------------------");

            while (rs.next()) {

                int id = rs.getInt("id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String email = rs.getString("email");
                String dept = rs.getString("department");
                double salary = rs.getDouble("salary");

                System.out.printf("%d | %s | %s | %s | %s | %.2f\n",
                        id, first, last, email, dept, salary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // UPDATE
    public void updateSalary(String firstName, double salary) {

        String sql = "UPDATE employees SET salary = ? WHERE first_name = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, salary);
            ps.setString(2, firstName);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee updated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // DELETE
    public void deleteEmployee(String firstName) {

        String sql = "DELETE FROM employees WHERE first_name = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, firstName);

            int rows = ps.executeUpdate();
            System.out.println(rows + " employee deleted");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FIND EMPLOYEE BY ID
    public void findEmployeeById(int id) {

        String sql = "SELECT * FROM employees WHERE id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int empId = rs.getInt("id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String email = rs.getString("email");
                String dept = rs.getString("department");
                double salary = rs.getDouble("salary");

                System.out.println("\nEmployee Found:");
                System.out.printf("%d | %s | %s | %s | %s | %.2f\n",
                        empId, first, last, email, dept, salary);

            } else {
                System.out.println("Employee not found with id: " + id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}