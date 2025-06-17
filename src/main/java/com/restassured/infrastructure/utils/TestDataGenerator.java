package com.restassured.infrastructure.utils;

import java.util.UUID;

public class TestDataGenerator {

    public static String generateRandomEmail() {
        return "user_" + UUID.randomUUID() + "@example.com";
    }

    public static String generateRandomPassword() {
        return "Pass" + UUID.randomUUID().toString().substring(0, 8);
    }
} 