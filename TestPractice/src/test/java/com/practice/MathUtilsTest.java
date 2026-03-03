package com.practice;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class MathUtilsTest {
    MathUtils mathUtils;

    @BeforeAll
    static void initAll() {
        System.out.println("BeforeAll method runs : Initializing class level resources");
    }

    @AfterAll
    static void tearAll() {
        System.out.println("Closing all class-level variables");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach method runs : Creating fresh MathUtils instance");
        mathUtils = new MathUtils();
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach method runs : Cleaning up test resources");
        mathUtils = null;
    }

    @Test
    @DisplayName("Adding 2+3 should equal to 5")
    void add() {
        System.out.println("Running testAdd");
        int result = mathUtils.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Multiplying 2*3 should equal to 6")
    void multiply() {
        System.out.println("Running testMultiply");
        int result = mathUtils.multiply(2, 3);
        assertEquals(6, result);
    }

    @Test
    @DisplayName("Second value should be less than the first one")
    void testMulBySecondValueHigher(){
        System.out.println("Running test subtract");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () ->
                        mathUtils.sub(2, 3)
        );
        assertEquals("Second value should be less than first one", exception.getMessage());
    }

    @Test
    @DisplayName("Division by zero throws IllegalArgumentException")
    void testDivByZero(){
        System.out.println("Running test Division");
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> mathUtils.div(10, 0)
        );

        assertEquals("You are a mad programmer",exception.getMessage());
    }
}