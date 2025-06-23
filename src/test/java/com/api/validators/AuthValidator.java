package com.api.validators;

import com.api.models.User;
import com.api.models.response.LoginResponse;
import com.api.models.response.RegisterResponse;
import io.restassured.response.Response;
import org.testng.Assert;

public class AuthValidator {


    // ✅ Login հաջողության դեպքում՝ հիմնական message և token ստուգում
    public static void validateSuccessfulLogin(LoginResponse response) {
        CommonValidator.assertFieldEquals(response.getMessage(), "Login successful", "message");
        String token = response.getToken();
        validateTokenFormat(token); // ✅ JWT check

        CommonValidator.assertNotNull(response.getUser(), "user");
        UserValidator.validateUserObject(response.getUser());
    }

    // ✅ Register հաջողության դեպքում՝ status code, message, user
    public static void validateRegisterSuccess(Response response, RegisterResponse body) {
        CommonValidator.assertStatusCode(response, 201);
        CommonValidator.assertNotEquals(body.getMessage(), "User registered successfully", "message");

        String token = body.getToken();
        validateTokenFormat(token);

        CommonValidator.assertNotNull(body.getUser(), "user");
        UserValidator.validateUserObject(body.getUser());
    }

    // ✅ Register failure – user already exists
    public static void validateRegisterFailure(Response response, RegisterResponse body) {
        CommonValidator.assertStatusCode(response, 400);
        CommonValidator.assertEquals(body.getMessage(), "User with this email already exists", "message");
    }

    // ✅ Login user email check + delegate full user validation
    public static void validateUserInfo(User user, String expectedEmail) {
        CommonValidator.assertNotNull(user, "user object");
        CommonValidator.assertFieldEquals(user.getEmail(), expectedEmail, "email");
        UserValidator.validateUserObject(user);
    }

    // ✅ Login failure-ի դեպքում՝ ստուգում ենք message և details
    public static void validateInvalidLogin(LoginResponse response, String expectedMessage, String expectedDetails) {
        CommonValidator.assertFieldEquals(response.getMessage(), expectedMessage, "message");
        CommonValidator.assertFieldEquals(response.getDetails(), expectedDetails, "details");
    }

    // ✅ JWT token ձևաչափի ստուգում
    public static void validateTokenFormat(String token) {
        CommonValidator.assertNotNull(token, "token");
        CommonValidator.assertNotEmpty(token, "token");
        String[] parts = token.split("\\.");
        Assert.assertEquals(parts.length, 3, "❌ Token must follow JWT format (header.payload.signature)");
    }
}
