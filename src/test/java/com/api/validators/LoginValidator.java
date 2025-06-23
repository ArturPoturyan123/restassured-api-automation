package com.api.validators;

import com.api.models.User;
import com.api.models.response.LoginResponse;

public class LoginValidator {

    public static void validateSuccessfulLogin(LoginResponse response) {
        CommonValidator.assertFieldEquals(response.getMessage(), "Login successful", "message");
        CommonValidator.assertNotNull(response.getToken(), "token");
        CommonValidator.assertNotEmpty(response.getToken(), "token");
    }

    public static void validateUserInfo(LoginResponse response, String expectedEmail) {
        CommonValidator.assertNotNull(response.getUser(), "user object");
        CommonValidator.assertFieldEquals(response.getUser().getEmail(), expectedEmail, "email");
        UserValidator.validateUserObject(response.getUser());
    }

    public static void validateInvalidLogin(LoginResponse response, String expectedMessage, String expectedDetails) {
        CommonValidator.assertFieldEquals(response.getMessage(), expectedMessage, "message");
        CommonValidator.assertFieldEquals(response.getDetails(), expectedDetails, "details");
    }

    public static void validateTokenFormat(String token) {
        CommonValidator.assertNotNull(token, "token");
        String[] parts = token.split("\\.");
        org.testng.Assert.assertEquals(parts.length, 3, "❌ Token must follow JWT format");
    }
}
