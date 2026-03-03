package com.practice;

public class LoanDecisionService {
    public LoanDecision evaluateLoanDecision(Customer customer) {
        boolean approved = customer.getCreditScore() >= 700;
        String risk   = customer.getCreditScore()>=750 ? "LOW" :"MEDIUM";
        double rate = risk.equals("LOW") ? 8.5 : 10.5 ;
        return  new LoanDecision(approved,risk,rate);
    }
}
