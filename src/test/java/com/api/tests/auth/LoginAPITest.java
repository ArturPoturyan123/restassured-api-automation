package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.services.AuthService;
import com.api.models.response.LoginResponse;
import com.api.models.request.LoginRequest;
import com.api.utils.ConfigReader;
import com.api.utils.TestDataGenerator;
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
        Assert.assertEquals(response.getStatusCode(),
                200, "Unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), "Login successful",
                "Login message mismatch");
        Assert.assertNotNull(loginResponse.getToken(), "Token is null");
        Assert.assertFalse(loginResponse.getToken().isEmpty(), "Token is empty");
    }


    @Test(description = "wrong email correct password")
    public void shouldFailLoginWithInvalidEmail() {
        String wrongEmail = "wrongEmail";
        String correctPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(wrongEmail, correctPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                401, "unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getDetails(),
                "No user found with this email address", "Login message mismatch");
        Assert.assertEquals(loginResponse.getMessage(),
                "Invalid email or password", "incorrect");


    }


    @Test(description = "✅ Verify login returns correct user email")
    public void loginReturnsCorrectEmail() {


        String expectedEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(expectedEmail, password);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                200, "Unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertNotNull(loginResponse.getUser(), "User object is null");
        Assert.assertEquals(loginResponse.getUser().getEmail(),
                expectedEmail, "Returned email does not match login email");


    }

    @Test(description = "✅ Verify User object populated correctly upon login ")
    public void shouldUserObjectPopulatedCorrectly() {

        String loginEmail = ConfigReader.get("loginEmail");
        String loginPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, loginPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                200, "Unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);

        Assert.assertNotNull(loginResponse.getUser().get_id(), "user id is null");
        Assert.assertFalse(loginResponse.getUser().get_id().isEmpty(), "user id is not valid");

        Assert.assertNotNull(loginResponse.getUser().getAge(), "user age is null");
        Assert.assertTrue(loginResponse.getUser().getAge() > 0, "user age is not valid");

        Assert.assertNotNull(loginResponse.getUser().get__v(), "user __v is null");
        Assert.assertTrue(loginResponse.getUser().get__v() >= 0, "user __v is not valid");

        Assert.assertNotNull(loginResponse.getUser().getAddress(), "user address is null");
        Assert.assertFalse(loginResponse.getUser().getAddress().isEmpty(), "user address is not valid");

        Assert.assertNotNull(loginResponse.getUser().getName(), "user name is null");
        Assert.assertFalse(loginResponse.getUser().getName().isEmpty(), "user name is not valid");

        Assert.assertNotNull(loginResponse.getUser().getCreatedAt(), "user createdAt is null");
        Assert.assertFalse(loginResponse.getUser().getCreatedAt().isEmpty(), "user createdArt is not valid");

        Assert.assertNotNull(loginResponse.getUser().getUpdatedAt(), "user updatedAt is null");
        Assert.assertFalse(loginResponse.getUser().getUpdatedAt().isEmpty(), "user updatedAt is not valid");


    }


    @Test(description = "✅ Verify login generates a new token on each login")
    public void shouldReturnSameTokenOnConsecutiveLogins() {

        String loginEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");

        loginRequest = new LoginRequest(loginEmail, password);

        //first login
        Response firstResponse = authService.login(loginRequest);
        Assert.assertEquals(firstResponse.getStatusCode(), 200, "Unexpected status code");
        String firstToken = firstResponse.as(LoginResponse.class).getToken();
        Assert.assertNotNull(firstToken, "First token is null");

        //second login
        Response secondResponse = authService.login(loginRequest);
        Assert.assertEquals(secondResponse.getStatusCode(), 200, "Unexpected status code");
        String secondToken = secondResponse.as(LoginResponse.class).getToken();

        Assert.assertNotNull(secondToken, "Second token is null");
        // Assert tokens not equal
        Assert.assertNotEquals(secondToken, firstToken, "Tokens should not match for separate logins");


    }


    @Test(description = "✅ Verify login fails with incorrect password")
    public void shouldLoginFailsWithIncorrectPassword() {

        String loginEmail = ConfigReader.get("loginEmail");
        String wrongPassword = TestDataGenerator.generateRandomPassword();
        loginRequest = new LoginRequest(loginEmail, wrongPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                401, "Unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(),
                "Invalid email or password", "Error message mismatch");
        Assert.assertEquals(loginResponse.getDetails(),
                "The provided password is incorrect", "Details message mismatch");

    }


    @Test(description = "✅ Verify login returns valid JWT token format")
    public void shouldReturnValidJwtTokenFormat() {
        String loginEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, password);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                200, "unexpected status code");

        String token = response.as(LoginResponse.class).getToken();
        Assert.assertNotNull(token, "Token is null");
        Assert.assertFalse(token.isEmpty(), "Token is empty");
        String[] tokenParts = token.split("\\.");
        Assert.assertEquals(tokenParts.length, 3,
                "Invalid JWT format (should have 3 parts separated by dots)");


    }

    @Test(description = "❌ Verify login fails with missing password")
    public void shouldFailLoginWhenPasswordIsMissing() {
        String loginEmail = ConfigReader.get("loginEmail");
        String emptyPassword = "";
        loginRequest = new LoginRequest(loginEmail, emptyPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 400, "Unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertTrue(loginResponse.getMessage().contains("Email and password are required"),
                "Error message mismatch");
        Assert.assertTrue(loginResponse.getDetails().contains("Please provide both email and password"),
                "Details message mismatch");


    }

    @Test(description = "❌ Verify login fails with missing email")

    public void shouldFailLoginWhenEmailIsMissing() {

        String emptyEmail = "";
        String loginPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(emptyEmail, loginPassword);
        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(),
                400, "unexpected status code");
        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(),
                "Email and password are required", "Error message mismatch");
        Assert.assertEquals(loginResponse.getDetails(),
                "Please provide both email and password", "Details message mismatch");


    }

}