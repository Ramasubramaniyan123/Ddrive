package com.practice.ex2;

public class DataBaseService {
    DataBaseConfig dataBaseConfig;

    public void setDataBaseConfig(DataBaseConfig dataBaseConfig) {
        this.dataBaseConfig = dataBaseConfig;
    }

    public void connect(){
        System.out.println("Connect Database..........");
        dataBaseConfig.printConfig();
        System.out.println("Connection Established...");
    }
}
