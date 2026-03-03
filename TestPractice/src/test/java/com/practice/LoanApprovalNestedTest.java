package com.practice;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoanApprovalNestedTest {
    private final LoanApprovalService loanApprovalService = new LoanApprovalService();

    @Nested
    class ValidApplications {

        @Test
        void shouldApproveWhenAllConditionsSatisfied() {
            //Arrange
            Customer customer = new Customer("Ram", 30, 750, 900000, false);
            //Act
            LoanApplication application = new LoanApplication(customer, 500000);
            //Assert
            assertTrue(loanApprovalService.approve(application));
        }
    }

    @Nested
    class BusinessRuleFailureTests {

        @Test
        void shouldRejectWhenCreditScoreIsLow() {

            // Arrange
            Customer customer = new Customer("John", 30, 650, 900000, false);

            LoanApplication application = new LoanApplication(customer, 500000);

            // Act
            boolean result = loanApprovalService.approve(application);

            // Assert
            assertFalse(result);
        }

        @Test
        void shouldRejectWhenCustomerHasActiveLoan() {

            // Arrange
            Customer customer = new Customer("Priya", 40, 780, 1200000, true);

            LoanApplication application = new LoanApplication(customer, 200000);

            // Act
            boolean result = loanApprovalService.approve(application);

            // Assert
            assertFalse(result);
        }

        @Test
        void shouldRejectWhenAgeIsBelowMinimum() {

            // Arrange
            Customer customer = new Customer("Arun", 19, 800, 1000000, false);

            LoanApplication application = new LoanApplication(customer, 300000);

            // Act
            boolean result = loanApprovalService.approve(application);

            // Assert
            assertFalse(result);
        }
    }
    @Nested
    class InvalidInputTests {

        @Test
        void shouldThrowExceptionWhenLoanApplicationIsNull() {

            // Arrange
            LoanApplication application = null;

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> loanApprovalService.approve(application));
        }

        @Test
        void shouldThrowExceptionWhenCustomerIsNull() {

            // Arrange
            LoanApplication application =
                    new LoanApplication(null, 500000);

            // Act & Assert
            assertThrows(IllegalArgumentException.class,
                    () -> loanApprovalService.approve(application));
        }
    }
}
