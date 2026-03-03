package com.practice.loan;

public interface LoanRepository {
    void save(LoanApplication loan);

    LoanApplication findById(int i);
}
