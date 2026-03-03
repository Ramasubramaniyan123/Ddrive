package com.practice;

public class FraudService {
    public boolean isBlocked(double fraudScore){
        return fraudScore > 0.8;
    }
}
