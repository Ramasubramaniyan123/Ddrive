package com.training.mybank.util;

public class PasswordUtil {

    public static String hash(String rawPassword) {
        return rawPassword;
    }

    public static boolean matches(String raw, String stored) {
        if (raw == null || stored == null) return false;
        return raw.equals(stored);
    }
}
