package com.practice;

import java.sql.SQLException;

public class AccountDemo {
    public static void main(String[] args) {
        AccountService service = new AccountService();
        try{
            service.printBalances();
            service.transfer(4,7, 2000);
            service.printBalances();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
