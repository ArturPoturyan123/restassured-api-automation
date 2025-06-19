package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.services.AuthService;
import com.api.models.response.LoginResponse;
import com.api.models.request.LoginRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAPITest extends TestBase {
    AuthService authService = new AuthService();


    @Test(description = "Verify if Login API is working...")
    public void loginTest() {
        // Prepare the request payload
        LoginRequest loginRequest = new LoginRequest("poturr@gmail.com", "Killeradmin123");

        // Call the login service
        Response response = authService.login(loginRequest);

        // Deserialize the response
        LoginResponse loginResponse = response.as(LoginResponse.class);

        // Assert that login was successful
        Assert.assertEquals(loginResponse.getMessage(), "Login successful", "Login message mismatch");

        // Assert that token is present and not empty
        Assert.assertNotNull(loginResponse.getToken(), "Token is null");
        Assert.assertFalse(loginResponse.getToken().isEmpty(), "Token is empty");
    }
} 