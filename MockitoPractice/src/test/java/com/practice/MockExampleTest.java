package com.practice;

import com.practice.loan.LoanApplication;
import com.practice.loan.LoanRepository;
import com.practice.loan.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MockExampleTest {

    @Mock
    LoanRepository  loanRepository;

    @InjectMocks
    LoanService  loanService;

    @Test
    void shouldCallSaveWhenApproved(){
        LoanApplication loan = new LoanApplication("Ram",760,false);
        loanService.processLoan(loan);
        verify(loanRepository).save(loan);
    }
}
