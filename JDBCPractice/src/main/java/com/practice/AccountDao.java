package com.practice;

import java.sql.SQLException;

public class AccountDao {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        try {
            System.out.println("Before transaction");
            accountService.printBalance();

            accountService.transfer(2,1,2000);

            System.out.println("After transaction");
            accountService.printBalance();

        } catch (SQLException e) {
            accountService.printBalance();

           e.printStackTrace();
        }
    }
}
