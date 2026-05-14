package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String username = "root";
        String password = "Ram@2005";
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful");
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
