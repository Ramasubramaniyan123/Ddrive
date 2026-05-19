package com.learning.spring.aspect;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

public class PaymentService {
    public void makePayment() {
        System.out.println("Processing Payment...");
    }

    public void checkBalance() {
        System.out.println("Checking Balance...");
    }
}
