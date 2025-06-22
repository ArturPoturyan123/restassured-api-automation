package com.api.validators;

import com.api.models.response.LoginResponse;

public class LoginValidator {

    public static void validateSuccessfulLogin(LoginResponse response) {
        CommonValidator.assertFieldEquals(response.getMessage(), "Login successful", "message");
        CommonValidator.assertNotNull(response.getToken(), "token");
    }

    public static void validateUserInfo(LoginResponse response, String expectedEmail) {
        CommonValidator.assertNotNull(response.getUser(), "user object");

        CommonValidator.assertFieldEquals(response.getUser().getEmail(), expectedEmail, "email");
        CommonValidator.assertNotNull(response.getUser().get_id(), "user._id");
        CommonValidator.assertNotNull(response.getUser().getAge(), "user.age");
        CommonValidator.assertNotNull(response.getUser().get__v(), "user.__v");
        CommonValidator.assertNotNull(response.getUser().getAddress(), "user.address");
        CommonValidator.assertNotNull(response.getUser().getName(), "user.name");
        CommonValidator.assertNotNull(response.getUser().getCreatedAt(), "user.createdAt");
        CommonValidator.assertNotNull(response.getUser().getUpdatedAt(), "user.updatedAt");
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
