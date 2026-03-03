package com.practice;


import com.practice.loan.LoanApplication;
import com.practice.loan.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StubExampleTest {

    @Mock
    LoanRepository loanRepository;

    @Test
    void stubExampleTest() {
        LoanApplication loan = new LoanApplication("Ram",760,false);
        when(loanRepository.findById(1)).thenReturn(loan);
        LoanApplication result = loanRepository.findById(1);
        assertEquals("Ram", result.getAppliantName());
    }
}
