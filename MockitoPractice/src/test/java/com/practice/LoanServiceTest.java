package com.practice;

import com.practice.loan.LoanApplication;
import com.practice.loan.LoanRepository;
import com.practice.loan.LoanService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanService service;

    @Test
    void shouldApproveLoanWhenCreditScoreIsHigh(){
        //Arrange
        LoanApplication application = new LoanApplication("Ram", 800, false);
        //Act
        boolean result = service.processLoan(application);
        //Assert
        assertTrue(result);
        assertTrue(application.isApproved());
        verify(loanRepository).save(application);
    }
}
