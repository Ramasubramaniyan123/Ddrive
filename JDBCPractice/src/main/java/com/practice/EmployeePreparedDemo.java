package com.practice;

public class EmployeePreparedDemo {

    public static void main(String[] args) {

        EmployeeDao dao = new EmployeeDao();

        // CREATE
        dao.addEmployee("Alice", "Johnson", "alice@gmail.com", "IT", 65000);

        // READ
        dao.listEmployees();

        // UPDATE
        dao.updateSalary("Alice", 70000);

        // READ AGAIN
        dao.listEmployees();

        // FIND BY ID
        dao.findEmployeeById(345);

        // DELETE
        dao.deleteEmployee("Alice");

        // FINAL READ
        dao.listEmployees();
    }
}
