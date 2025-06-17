package com.restassured.tests.auth;

import com.restassured.tests.TestBase;
import com.restassured.infrastructure.services.AuthService;
import com.restassured.domain.entities.LoginResponse;
import com.restassured.domain.entities.LoginRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAPITest extends TestBase {

    @Test(description = "Verify if Login API is working...")
    public void loginTest3() {
        LoginRequest loginRequest = new LoginRequest("poturr@gmail.com", "Killeradmin123");
        AuthService authService = new AuthService();
        Response response = authService.login(loginRequest);
        LoginResponse loginResponse = response.as(LoginResponse.class);
        System.out.println(response.asPrettyString());
        System.out.println(loginResponse.getMessage());
        System.out.println(loginResponse.getToken());
        Assert.assertEquals(loginResponse.getMessage(),"Login successful");
    }
} 