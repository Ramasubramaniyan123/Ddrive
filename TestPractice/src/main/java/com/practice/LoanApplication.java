package com.practice;

public class LoanApplication {
    private Customer customer;
    private double requestedAmount;

    public LoanApplication(Customer customer, double requestedAmount) {
        this.customer = customer;
        this.requestedAmount = requestedAmount;
    }
    public Customer getCustomer() {
        return customer;
    }
    public double getRequestedAmount() {
        return requestedAmount;
    }

}
