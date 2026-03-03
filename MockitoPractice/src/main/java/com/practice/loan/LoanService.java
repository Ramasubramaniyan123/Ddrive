package com.practice.loan;

public class LoanService {
    private   LoanRepository loanRepository;
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }
    public boolean processLoan(LoanApplication loan){
        if(loan == null){
            throw new IllegalArgumentException("Loan application cannot be null");
        }
        if(loan.getCreditScore()>=700){
            loan.setApproved(true);
            loanRepository.save(loan);
            return true;
        }
        return false;
    }
}
