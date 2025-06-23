package com.api.tests.auth;

import com.api.base.TestBase;
import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import com.api.services.AuthService;
import com.api.utils.ConfigReader;
import com.api.utils.TestDataGenerator;
import com.api.validators.AuthValidator;
import com.api.validators.CommonValidator;
import com.api.validators.UserValidator;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login Endpoint")
@Listeners({io.qameta.allure.testng.AllureTestNg.class})
public class LoginAPITest extends TestBase {

    private AuthService authService;
    private LoginRequest loginRequest;
    private String email;
    private String password;

    @BeforeMethod
    public void setUp() {
        authService = new AuthService();
        email = ConfigReader.get("loginEmail");
        password = ConfigReader.get("loginPassword");
    }

    @Test(description = "✅ Verify login with valid credentials")
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that login succeeds with correct email and password, and returns token.")
    public void shouldReturnSuccessMessageAndTokenForValidLogin() {
        loginRequest = new LoginRequest(email, password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 200);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateSuccessfulLogin(loginResponse);
    }

    @Test(description = "✅ Login returns correct email and user object")
    @Story("Successful Login")
    @Severity(SeverityLevel.MINOR)
    @Description("Ensure that the user email returned matches the one used to login.")
    public void shouldReturnCorrectUserInfo() {
        loginRequest = new LoginRequest(email, password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 200);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateUserInfo(loginResponse.getUser(), email);
    }

    @Test(description = "✅ User object structure validation")
    @Story("Successful Login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checks if user object contains required fields on successful login.")
    public void shouldUserObjectBePopulatedCorrectly() {
        loginRequest = new LoginRequest(email, password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 200);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        UserValidator.validateUserObject(loginResponse.getUser());
    }

    @Test(description = "✅ Verify token changes on each login")
    @Story("Token Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensure login generates a new token each time.")
    public void shouldReturnDifferentTokenOnEachLogin() {
        loginRequest = new LoginRequest(email, password);

        String firstToken = authService.login(loginRequest).as(LoginResponse.class).getToken();
        String secondToken = authService.login(loginRequest).as(LoginResponse.class).getToken();

        AuthValidator.validateTokenFormat(firstToken);
        AuthValidator.validateTokenFormat(secondToken);
        CommonValidator.assertNotEquals(firstToken, secondToken,
                "❌ Tokens should be different on separate logins");
    }

    @Test(description = "✅ Token format validation")
    @Story("Token Management")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensure returned token follows JWT format (3 dot-separated parts).")
    public void shouldReturnValidJwtTokenFormat() {
        loginRequest = new LoginRequest(email, password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 200);

        String token = response.as(LoginResponse.class).getToken();
        AuthValidator.validateTokenFormat(token);
    }

    @Test(description = "❌ Incorrect email")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail if email is incorrect even when password is correct.")
    public void shouldFailLoginWithInvalidEmail() {
        String wrongEmail = "wrong@email.com";
        loginRequest = new LoginRequest(wrongEmail, password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 401);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateInvalidLogin(
                loginResponse,
                "Invalid email or password",
                "No user found with this email address"
        );
    }

    @Test(description = "❌ Incorrect password")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login should fail when password is incorrect.")
    public void shouldFailLoginWithInvalidPassword() {
        String wrongPassword = TestDataGenerator.generateRandomPassword();
        loginRequest = new LoginRequest(email, wrongPassword);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 401);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateInvalidLogin(
                loginResponse,
                "Invalid email or password",
                "The provided password is incorrect"
        );
    }

    @Test(description = "❌ Missing password")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail when password field is empty.")
    public void shouldFailLoginWhenPasswordIsMissing() {
        loginRequest = new LoginRequest(email, "");
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 400);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateInvalidLogin(
                loginResponse,
                "Email and password are required",
                "Please provide both email and password"
        );
    }

    @Test(description = "❌ Missing email")
    @Story("Negative Login")
    @Severity(SeverityLevel.NORMAL)
    @Description("Login must fail when email is missing.")
    public void shouldFailLoginWhenEmailIsMissing() {
        loginRequest = new LoginRequest("", password);
        Response response = authService.login(loginRequest);
        CommonValidator.assertStatusCode(response, 400);

        LoginResponse loginResponse = response.as(LoginResponse.class);
        AuthValidator.validateInvalidLogin(
                loginResponse,
                "Email and password are required",
                "Please provide both email and password"
        );
    }
}
