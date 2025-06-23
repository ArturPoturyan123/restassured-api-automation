package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.models.request.RegisterRequest;
import com.api.models.response.RegisterResponse;
import com.api.services.AuthService;
import com.api.utils.TestDataGenerator;
import com.api.validators.AuthValidator;
import com.api.validators.CommonValidator;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import jdk.jfr.Description;
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

        AuthValidator.validateRegisterSuccess(response, responseBody);

    }

    @Test(description = "❌ Registration should fail if email already exists")
    @Story("Negative Registration")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensure registration fails if user email is already registered.")
    public void shouldReturn400IfEmailAlreadyExists() {
        // First register user
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(TestDataGenerator.generateRandomEmail());
        registerRequest.setPassword(TestDataGenerator.generateRandomPassword());

        authService.register(registerRequest); // First successful attempt

        // Try registering same user again
        Response response = authService.register(registerRequest);
        RegisterResponse responseBody = response.as(RegisterResponse.class);

        AuthValidator.validateRegisterFailure(response, responseBody);
    }
}
