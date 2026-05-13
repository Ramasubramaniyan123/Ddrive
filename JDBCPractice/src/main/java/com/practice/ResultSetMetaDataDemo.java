package com.practice;

import java.sql.*;

public class ResultSetMetaDataDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        //String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String pass = "Ram@2005";
        String sql = "select * from  employees";
        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql);
        ) {
            ResultSetMetaData metaData = resultSet.getMetaData();

            System.out.println("Total Columns: " + metaData.getColumnCount());
            System.out.println("\nColumn Information: \n");
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                System.out.println(metaData.getColumnName(i) + " " + metaData.getColumnTypeName(i));
            }

            System.out.println("\nEmployee Information: \n");
            System.out.println("Absolute =============" +resultSet.absolute(2));
            while (resultSet.next()) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    System.out.print(resultSet.getString(i) + " | ");
                }
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
