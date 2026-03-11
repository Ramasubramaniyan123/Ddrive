package com.practice;

import java.sql.*;

public class AccountService {
    String url = "jdbc:mysql://localhost:3306/jdbc";
    String user = "root";
    String pass = "Ram@2005";

    public void transfer(int from, int to, double amount) throws SQLException {
        if(amount > getBalance(from)){
            throw new SQLException("Your balance is less to transfer");
        }

        String withdraw = "update accounts set balance = balance - ? where acc_id = ?";
        String deposit = "update accounts set balance = balance + ? where acc_id = ?";
        Connection connection = DriverManager.getConnection(url, user, pass);
        try {
            connection.setAutoCommit(false);

            PreparedStatement withdrawStmt = connection.prepareStatement(withdraw);
            PreparedStatement depositStmt = connection.prepareStatement(deposit);

            withdrawStmt.setDouble(1, amount);
            withdrawStmt.setInt(2, from);
            withdrawStmt.executeUpdate();

            depositStmt.setDouble(1, amount);
            depositStmt.setInt(2, to);
            depositStmt.executeUpdate();

            connection.commit();
            System.out.println("Transaction successful");
        } catch (Exception e) {
            connection.rollback();
            e.printStackTrace();
        } finally {
            connection.close();
        }

    }

    public void printBalance() {
        String sql = "select * from accounts";

        try (Connection connection = DriverManager.getConnection(url, user, pass);) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("acc_id") + " " + resultSet.getString("name") + " " + resultSet.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getBalance(int id){
        String sql = "select balance from accounts where acc_id = ?";
        double balance = 0;
        try(Connection connection = DriverManager.getConnection(url,user,pass);
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                balance = resultSet.getDouble("balance");
            }

        }
        catch (SQLException e){

            e.printStackTrace();
        }
        return  balance;
    }

}
