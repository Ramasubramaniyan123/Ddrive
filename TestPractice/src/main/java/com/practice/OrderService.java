package com.practice;

public class OrderService {
    public boolean canCancel(OrderStatus status){
        return status == OrderStatus.CREATED || status == OrderStatus.PAYMENT_PENDING;
    }
}
