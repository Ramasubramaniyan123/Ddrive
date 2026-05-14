package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeBatchInsert {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Ram@2005";
        EmployeeBatchInsert obj = new EmployeeBatchInsert();
        long individualBefore = System.currentTimeMillis();
        obj.individualInsertEmployees(url, username, password);
        long individualAfter = System.currentTimeMillis();
        long totalIndividual = individualAfter - individualBefore;
        long batchBefore = System.currentTimeMillis();
        obj.batchInsertEmployees(url, username, password);
        long batchAfter = System.currentTimeMillis();
        long totalBatches = batchAfter - batchBefore;

        System.out.println("Total batches: " + totalBatches);
        System.out.println("Total individual batches: " + totalIndividual);
    }

    public void batchInsertEmployees(String url, String username, String password) throws SQLException {
        String sql = "insert into employee_batch_1 (emp_id, name,salary) values (?, ?, ? )";
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);

            for (int i = 1; i <= 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "EMPLOYEE" + i);
                preparedStatement.setDouble(3, i + 1000);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            if(connection != null){
                connection.rollback();
            }
            e.printStackTrace();
        }
    }

    public void individualInsertEmployees(String url, String username, String password) {
        String sql = "insert into employee_batch_2 (emp_id, name,salary) values (?, ?, ? )";
        Connection connection = null;
        try {
             connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            for (int i = 1; i <= 10000; i++) {
                preparedStatement.setInt(1, i);
                preparedStatement.setString(2, "EMPLOYEE" + i);
                preparedStatement.setDouble(3, i + 1000);
                preparedStatement.executeUpdate();
            }
            connection.commit();
            connection.setAutoCommit(true);
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            try{
                if(connection != null) {
                    connection.rollback();
                }
            }
            catch (SQLException ex){
                e.printStackTrace();
            }
            e.printStackTrace();
        }
    }
}
