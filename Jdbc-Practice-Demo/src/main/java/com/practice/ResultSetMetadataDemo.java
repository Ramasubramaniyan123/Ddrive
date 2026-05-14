package com.practice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetMetadataDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";
        String sql =
                "SELECT emp_id, name, salary " +
                        "FROM employee " +
                        "WHERE salary > ? " +
                        "ORDER BY salary DESC";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setDouble(1, 60000);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("COLUMN DETAILS");
            System.out.println("==============");

            for(int i = 1; i <= columnCount; i++) {
                System.out.println(
                        "Column Name : " +
                                metaData.getColumnName(i)
                );

                System.out.println(
                        "Column Type : " +
                                metaData.getColumnTypeName(i)
                );

                System.out.println("--------------------");
            }

            System.out.println("\nEMPLOYEE DATA");
            System.out.println("==============");

            while(rs.next()) {

                for(int i = 1; i <= columnCount; i++) {

                    String columnName =
                            metaData.getColumnName(i);

                    Object value =
                            rs.getObject(i);

                    System.out.print(
                            columnName + " : " +
                                    value + "    "
                    );
                }

                System.out.println();
            }

            rs.close();
            ps.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}