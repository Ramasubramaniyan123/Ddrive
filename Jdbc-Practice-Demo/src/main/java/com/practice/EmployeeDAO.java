package com.practice;

import java.sql.*;

public class EmployeeDAO {
    private final String url = "jdbc:mysql://localhost:3306/jdbc";
    private final String username = "root";
    private final String password = "Ram@2005";

    public void addEmployee(String name, double salary) {
        String sql = "insert into employee (name, salary) values(?, ?)";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, salary);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public  void updateSalary(String name, double salary)  {
        String sql = "update employee set salary = ? where name = ?";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, salary);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public  void deleteEmployee(String name) throws SQLException {
        String sql = "delete from employee where name = ?";
        Connection connection = DriverManager.getConnection(url, username, password);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        try{
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        finally{
            preparedStatement.close();
            connection.close();
        }
    }

    public void listEmployees() throws SQLException{
        String sql = "select * from employee";
        Connection connection = DriverManager.getConnection(url, username, password);
        Statement statement = connection.createStatement();
        try{
            statement.executeQuery(sql);
            ResultSet resultSet = statement.getResultSet();
            while(resultSet.next()){
                System.out.println(resultSet.getString("name" ) + resultSet.getString("salary"));
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
