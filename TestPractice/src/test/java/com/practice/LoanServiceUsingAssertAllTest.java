package com.practice;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceUsingAssertAllTest {


    @Test
    void shouldEvaluateLoanDecision(){
        //Arrange
        Customer customer = new Customer("Ram",30,760,900000,false);
        LoanDecisionService loanDecisionService = new LoanDecisionService();

        //Act
        LoanDecision decision = loanDecisionService.evaluateLoanDecision(customer);
        //Assert
        assertAll("Loan Decision Validation",
                ()-> assertTrue (decision.isApproved()),
                () -> assertEquals("LOW",decision.getRiskCategory()),
                () -> assertEquals(8.5,decision.getInterestRate())
        );
    }
}
