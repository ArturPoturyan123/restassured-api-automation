package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.services.AuthService;
import com.api.models.response.LoginResponse;
import com.api.models.request.LoginRequest;
import com.api.utils.ConfigReader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginAPITest extends TestBase {
    private AuthService authService;
    private LoginRequest loginRequest;


    @BeforeMethod
    public void setUp() {
//        super.setUp();
        authService = new AuthService();

    }

    @Test(description = "Verify if Login API is working...")
    public void shouldReturnSuccessMessageAndTokenForValidLogin() {
        String loginEmail = ConfigReader.get("loginEmail");
        String loginPassword = ConfigReader.get("loginPassword");

        loginRequest = new LoginRequest(loginEmail, loginPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");

        LoginResponse loginResponse = response.as(LoginResponse.class);

        Assert.assertEquals(loginResponse.getMessage(), "Login successful",
                "Login message mismatch");
        Assert.assertNotNull(loginResponse.getToken(), "Token is null");
        Assert.assertFalse(loginResponse.getToken().isEmpty(), "Token is empty");
    }


    @Test(description = "wrong email correct password")
    public void shouldFailLoginWithInvalidEmail() {

        String wrongEmail = "test";
        String correctPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(wrongEmail, correctPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 401,
                "unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getDetails(),
                "No user found with this email address", "Login message mismatch");
        Assert.assertEquals(loginResponse.getMessage(),
                "Invalid email or password", "incorrect");


    }


}