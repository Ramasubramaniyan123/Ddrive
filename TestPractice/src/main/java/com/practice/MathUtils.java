package com.practice;

public class MathUtils {
    int add(int a, int b) {
        return a + b;
    }

    int multiply(int a, int b) {
        return a * b;
    }

    int sub(int a, int b) {
        if(b>a){
            throw new IllegalArgumentException("Second value should be less than first one");
        }
        return a - b;
    }

    int div(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("You are a mad programmer");
        }
        return a / b;
    }

}
