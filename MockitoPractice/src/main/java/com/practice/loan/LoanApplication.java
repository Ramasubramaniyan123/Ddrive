package com.practice.loan;

public class LoanApplication {
    private String appliantName;
    private int creditScore;
    private boolean approved;

    public LoanApplication(String appliantName, int creditScore, boolean approved) {
        this.appliantName = appliantName;
        this.creditScore = creditScore;
        this.approved = approved;
    }

    public String getAppliantName() {
        return appliantName;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
