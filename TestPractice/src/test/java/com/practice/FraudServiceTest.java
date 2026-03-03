package com.practice;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FraudServiceTest {
    @ParameterizedTest
    @ValueSource (doubles = {0.88,0.89,0.87})
    void shouldBlockedHighFraudScores(double score){
        FraudService fraudService = new FraudService();
        assertTrue(fraudService.isBlocked(score));
    }
}
