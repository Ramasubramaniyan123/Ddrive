package com.practice;

public class LoanDecision {
    private final boolean approved;
    private final String riskCategory;
    private final double interestRate;

    public LoanDecision(boolean approved, String riskCategory, double interestRate) {
        this.approved = approved;
        this.riskCategory = riskCategory;
        this.interestRate = interestRate;
    }
    public boolean isApproved() {
        return approved;
    }
    public String getRiskCategory() {
        return riskCategory;
    }
    public double getInterestRate() {
        return interestRate;
    }
}
