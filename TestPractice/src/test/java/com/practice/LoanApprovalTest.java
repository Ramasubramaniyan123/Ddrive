package com.practice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LoanApprovalTest {
    private final LoanApprovalService loanApprovalService = new LoanApprovalService();

    public static Stream<Arguments> loanApplicationProvider() {
        return Stream.of(
                Arguments.of(
                        new LoanApplication(
                                new Customer("Ram", 30, 750, 900000, false), 500000), true
                ),
                Arguments.of(
                        new LoanApplication(
                                new Customer("John", 35, 650, 1000000, false), 50000), false
                ),
                Arguments.of(
                        new LoanApplication(
                                new Customer(null, 35, 650, 1000000, false), 50000), false
                ),
                Arguments.of(
                        new LoanApplication(
                                new Customer("Priya", 40, 780, 1200000, true), 200000) , false
                )
        );

    }

    @ParameterizedTest
    @MethodSource("loanApplicationProvider")
    void shouldEvaluateeLoanApprovalCorrect(LoanApplication application, boolean expectedResult) {
        boolean actual = loanApprovalService.approve(application);
        assertEquals(expectedResult, actual);
    }

    @Test
    void shouldThrowExceptionWhenLoanApplicationIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> loanApprovalService.approve(null));
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNull() {
        LoanApplication application = new LoanApplication(null, 500000);

        assertThrows(IllegalArgumentException.class,
                () -> loanApprovalService.approve(application));
    }
}