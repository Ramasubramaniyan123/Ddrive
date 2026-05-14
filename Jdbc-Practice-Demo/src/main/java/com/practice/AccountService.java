package com.practice;

import javax.xml.transform.Result;
import java.sql.*;

public class AccountService {
    private String url = "jdbc:mysql://localhost:3306/jdbc";
    private String username = "root";
    private String password = "Ram@2005";

    public void transfer(int fromAccId, int toAccId, double amount) throws SQLException{
        if(amount <= 0){
            throw new SQLException("Amount must be positive");
        }
        if(fromAccId == toAccId){
            throw  new SQLException("can't transfer to same account");
        }
        String withDrawSql = "update accounts set balance = balance - ? where acc_id = ?";
        String depositSql = "update accounts set balance = balance + ? where acc_id = ?";
        String sql = "select * from accounts where acc_id = ?";
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, username, password);
            PreparedStatement withDrawStatement = connection.prepareStatement(withDrawSql);
            PreparedStatement depositStatement = connection.prepareStatement(depositSql);
            PreparedStatement statement = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            statement.setInt(1, fromAccId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                if(balance < amount) {
                    throw new SQLException("Insufficient balance");
                }

            } else {
                throw new SQLException("Sender account not found");
            }

            withDrawStatement.setDouble(1, amount);
            withDrawStatement.setInt(2, fromAccId);
            int withDrawRows = withDrawStatement.executeUpdate();

            depositStatement.setDouble(1,amount);
            depositStatement.setInt(2, toAccId);
            int depositRows = depositStatement.executeUpdate();
            if(withDrawRows==0 || depositRows==0){
                throw new SQLException("Transaction failed");
            }
            connection.commit();
            resultSet.close();
            depositStatement.close();
            withDrawStatement.close();
            statement.close();
        }
        catch (SQLException e){
            if(connection != null){
                connection.rollback();
            }
            System.out.println(e.getMessage());
        }
        finally {
            if(connection != null){
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }
    public void printBalances(){
        String sql ="select * from accounts";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                int id = resultSet.getInt("acc_id");
                String name = resultSet.getString("name");
                double balance = resultSet.getDouble("balance");
                System.out.println(id + " " + name + " " + balance);
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
