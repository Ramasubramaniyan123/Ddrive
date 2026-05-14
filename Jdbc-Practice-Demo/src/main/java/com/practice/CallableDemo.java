package com.practice;

import java.sql.*;

public class CallableDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Ram@2005";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, username, password);
            int empid = 101;
            String sql = "{call get_employee_salary(?,?)}";
            CallableStatement statement = connection.prepareCall(sql);
            statement.setInt(1, empid);
            statement.registerOutParameter(2, Types.DOUBLE);
            statement.execute();
            double salary = statement.getDouble(2);
            System.out.println("Employee salary" + salary);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
