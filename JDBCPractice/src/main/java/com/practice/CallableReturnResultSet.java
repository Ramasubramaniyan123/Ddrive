package com.practice;

import java.sql.*;

public class CallableReturnResultSet {
    public static void main(String[] args) {
        String sql = "{ call get_high_salary_employees(?) }";
        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        try (
                Connection con = DriverManager.getConnection(url, user, password);
                CallableStatement cs = con.prepareCall(sql)) {

            cs.setDouble(1, 0);

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                System.out.println(
                        rs.getInt("id") + " " +
                                rs.getString("name") + " " +
                                rs.getDouble("salary")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
