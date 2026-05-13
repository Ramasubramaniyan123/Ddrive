package com.practice.ex2;

public class DataBaseConfig {
    String url;
    String username;
    String password;

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public  void printConfig(){
        System.out.println("Database URL: "+ url);
        System.out.println("Database UserName: "+ username);
        System.out.println("Database Password: "+ password);
    }
}
