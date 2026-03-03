package com.practice;

public class LoanApprovalService {
    public boolean approve(LoanApplication loanApplication) {

        if (loanApplication == null || loanApplication.getCustomer() == null) {
            throw new IllegalArgumentException("Invalid loan application");
        }
        Customer customer = loanApplication.getCustomer();

        return customer.getCreditScore() >= 700
                && customer.getAnnualIncome() >= 800000
                && !customer.isHasActiveLoan()
                && customer.getAge() >= 21
                && customer.getAge() <= 60;
    }
}



