package com.practice;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderServiceTest {
    private final OrderService orderService = new OrderService();

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"CREATED", "PAYMENT_PENDING"})
    void shouldAllowCancellation(OrderStatus orderStatus) {
        assertTrue(orderService.canCancel(orderStatus));
    }

    @ParameterizedTest
    @EnumSource(value = OrderStatus.class, names = {"SHIPPED", "DELIVERED", "CANCELLED"})
    void shouldNotAllowCancellation(OrderStatus status) {
        assertFalse(orderService.canCancel(status));
    }
}
