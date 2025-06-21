package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.services.AuthService;
import com.api.models.response.LoginResponse;
import com.api.models.request.LoginRequest;
import com.api.utils.ConfigReader;
import com.api.utils.TestDataGenerator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login Endpoint")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class LoginAPITest extends TestBase {

    private AuthService authService;
    private LoginRequest loginRequest;

    @BeforeMethod
    public void setUp() {
        authService = new AuthService();
    }

    @Test(description = "✅ Verify login with valid credentials")
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that login succeeds with correct email and password, and returns token.")
    public void shouldReturnSuccessMessageAndTokenForValidLogin() {
        String loginEmail = ConfigReader.get("loginEmail");
        String loginPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, loginPassword);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), "Login successful", "Login message mismatch");
        Assert.assertNotNull(loginResponse.getToken(), "Token is null");
        Assert.assertFalse(loginResponse.getToken().isEmpty(), "Token is empty");
    }

    @Test(description = "❌ Wrong email, correct password")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail if email is incorrect even when password is correct.")
    public void shouldFailLoginWithInvalidEmail() {
        String wrongEmail = "wrongEmail";
        String correctPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(wrongEmail, correctPassword);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 401, "Unexpected status code");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getDetails(), "No user found with this email address", "Details mismatch");
        Assert.assertEquals(loginResponse.getMessage(), "Invalid email or password", "Message mismatch");
    }

    @Test(description = "✅ Login returns correct email")
    @Story("Successful Login")
    @Severity(SeverityLevel.MINOR)
    @Description("Ensure that the user email returned matches the one used to login.")
    public void loginReturnsCorrectEmail() {
        String expectedEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(expectedEmail, password);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code");

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertNotNull(loginResponse.getUser(), "User object is null");
        Assert.assertEquals(loginResponse.getUser().getEmail(), expectedEmail, "Email mismatch");
    }

    @Test(description = "✅ User object structure validation")
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checks if user object contains required fields on successful login.")
    public void shouldUserObjectPopulatedCorrectly() {
        String loginEmail = ConfigReader.get("loginEmail");
        String loginPassword = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, loginPassword);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertNotNull(loginResponse.getUser().get_id());
        Assert.assertFalse(loginResponse.getUser().get_id().isEmpty());

        Assert.assertNotNull(loginResponse.getUser().getAge());
        Assert.assertTrue(loginResponse.getUser().getAge() > 0);

        Assert.assertNotNull(loginResponse.getUser().get__v());
        Assert.assertTrue(loginResponse.getUser().get__v() >= 0);

        Assert.assertNotNull(loginResponse.getUser().getAddress());
        Assert.assertFalse(loginResponse.getUser().getAddress().isEmpty());

        Assert.assertNotNull(loginResponse.getUser().getName());
        Assert.assertFalse(loginResponse.getUser().getName().isEmpty());

        Assert.assertNotNull(loginResponse.getUser().getCreatedAt());
        Assert.assertFalse(loginResponse.getUser().getCreatedAt().isEmpty());

        Assert.assertNotNull(loginResponse.getUser().getUpdatedAt());
        Assert.assertFalse(loginResponse.getUser().getUpdatedAt().isEmpty());
    }

    @Test(description = "✅ Verify token changes on each login")
    @Story("Token Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensure login generates a new token each time.")
    public void shouldReturnSameTokenOnConsecutiveLogins() {
        String loginEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, password);

        Response firstResponse = authService.login(loginRequest);
        String firstToken = firstResponse.as(LoginResponse.class).getToken();

        Response secondResponse = authService.login(loginRequest);
        String secondToken = secondResponse.as(LoginResponse.class).getToken();

        Assert.assertNotNull(firstToken);
        Assert.assertNotNull(secondToken);
        Assert.assertNotEquals(firstToken, secondToken, "Tokens should be different on separate logins");
    }

    @Test(description = "❌ Incorrect password")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login should fail when password is incorrect.")
    public void shouldLoginFailsWithIncorrectPassword() {
        String loginEmail = ConfigReader.get("loginEmail");
        String wrongPassword = TestDataGenerator.generateRandomPassword();
        loginRequest = new LoginRequest(loginEmail, wrongPassword);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 401);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), "Invalid email or password");
        Assert.assertEquals(loginResponse.getDetails(), "The provided password is incorrect");
    }

    @Test(description = "✅ Token format validation")
    @Story("Token Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensure returned token follows JWT format (3 dot-separated parts).")
    public void shouldReturnValidJwtTokenFormat() {
        String loginEmail = ConfigReader.get("loginEmail");
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest(loginEmail, password);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 200);

        String token = response.as(LoginResponse.class).getToken();
        Assert.assertNotNull(token);
        String[] parts = token.split("\\.");
        Assert.assertEquals(parts.length, 3, "Invalid JWT format");
    }

    @Test(description = "❌ Missing password")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail when password field is empty.")
    public void shouldFailLoginWhenPasswordIsMissing() {
        String loginEmail = ConfigReader.get("loginEmail");
        loginRequest = new LoginRequest(loginEmail, "");

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 400);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertTrue(loginResponse.getMessage().contains("Email and password are required"));
        Assert.assertTrue(loginResponse.getDetails().contains("Please provide both email and password"));
    }

    @Test(description = "❌ Missing email")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail when email is missing.")
    public void shouldFailLoginWhenEmailIsMissing() {
        String password = ConfigReader.get("loginPassword");
        loginRequest = new LoginRequest("", password);

        Response response = authService.login(loginRequest);
        Assert.assertEquals(response.getStatusCode(), 400);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        Assert.assertEquals(loginResponse.getMessage(), "Email and password are required");
        Assert.assertEquals(loginResponse.getDetails(), "Please provide both email and password");
    }
}
