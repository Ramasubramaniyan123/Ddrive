package com.practice;

import java.sql.SQLException;

public class EmployeePreparedDAO {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();
        try {
            dao.addEmployee("Charlie", 50000);
            dao.addEmployee("Diana", 72000);

            dao.listEmployees();

            dao.updateSalary("Priya Sharma", 70000);
            dao.deleteEmployee("Karthik Raj");

            dao.listEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
