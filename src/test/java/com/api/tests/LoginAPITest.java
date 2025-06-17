package com.api.tests;

import base.TestBase;
import services.AuthService;
import com.api.models.response.LoginResponse;
import com.api.models.reuqest.LoginRequest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginAPITest extends TestBase {


//    @Test(description = "Verify if Login API is working...")
//    public void loginTest() {
//
//
//        RestAssured.baseURI = "https://crud-api-js-iqmx.onrender.com/";
//        RequestSpecification x = RestAssured.given();
//        RequestSpecification y = x.header("Content-Type", "application/json");
//        RequestSpecification z = y.body("{\"email\":\"poturr@gmail.com\",\"password\":\"Killeradmin123\"}");
//        Response response = z.post("/api/auth/login");
//        System.out.println(response.asPrettyString());
//        Assert.assertEquals(response.getStatusCode(),200);
//
//    }
//

//    @Test(description = "Verify if Login API is working...")
//    public void loginTest2() {
//
//        LoginRequest loginRequest = new LoginRequest("arthurp@doublecoconut.com", "123456");
//        AuthService authService = new AuthService();
//        Response response = authService.login(loginRequest);
//        System.out.println(response.asPrettyString());
//
//    }

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
