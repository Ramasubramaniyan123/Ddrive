package com.practice;

public class Customer {
    private String name;
    private int age;
    private int creditScore;
    private double annualIncome;
    private boolean hasActiveLoan;

    public Customer(String name, int age, int creditScore, double annualIncome, boolean hasActiveLoan) {
        this.name = name;
        this.age = age;
        this.creditScore = creditScore;
        this.annualIncome = annualIncome;
        this.hasActiveLoan = hasActiveLoan;
    }

    public int getAge() {
        return age;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public boolean isHasActiveLoan() {
        return hasActiveLoan;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }
}
