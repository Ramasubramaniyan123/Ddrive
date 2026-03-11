package com.practice;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class CallableDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/jdbc";
        String user = "root";
        String password = "Ram@2005";

        int empId = 1;

        String sql = " call get_employee_salary(?, ?) ";

        try (Connection con = DriverManager.getConnection(url, user, password);
             CallableStatement cs = con.prepareCall(sql)) {

            // IN parameter
            cs.setInt(1, empId);

            // OUT parameter
            cs.registerOutParameter(2, Types.DOUBLE);

            cs.execute();

            double salary = cs.getDouble(2);
            if(salary == -1){
                System.out.println("Employee not found");
            }else{
                System.out.println("Salary: " + salary);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}