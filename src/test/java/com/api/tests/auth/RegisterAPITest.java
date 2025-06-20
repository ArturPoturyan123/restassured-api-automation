package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.models.request.RegisterRequest;
import com.api.models.response.RegisterResponse;
import com.api.services.AuthService;
import com.api.utils.TestDataGenerator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RegisterAPITest extends TestBase {

    private AuthService authService;

    @BeforeMethod
    public void setUp() {

        super.setUp();
        authService = new AuthService();
    }

    @Test(description = "Verify that new user can register without token")
    public void shouldReturn201OnSuccessfulRegistration() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(TestDataGenerator.generateRandomEmail());
        registerRequest.setPassword(TestDataGenerator.generateRandomPassword());

        Response response = authService.register(registerRequest);


        RegisterResponse responseBody = response.as(RegisterResponse.class);
        Assert.assertEquals(response.getStatusCode(), 201, "Status code mismatch");

        Assert.assertEquals(responseBody.getMessage(), "User registered successfully",
                "register message mismatch");

    }

}
