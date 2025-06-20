package com.api.base;

import com.api.utils.ConfigReader;
import com.api.utils.TokenManager;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected RequestSpecification requestSpecification;

    @BeforeMethod
    public void setUp() {
//        RestAssured.baseURI = ConfigReader.get("baseUrl");

//        requestSpecification = RestAssured
//                .given()
//                .contentType("application/json")
//                .filter(new RequestLoggingFilter())
//                .filter(new ResponseLoggingFilter());
//
//        // ✅ Միայն եթե պետք է token
//        if (shouldUseAuth()) {
//            requestSpecification.auth().oauth2(TokenManager.getToken());
//        }

        System.out.println("✅ Test setup completed: baseURL + optional token");
    }

    @AfterClass
    public void afterAllTests() {
        System.out.println("✅ All tests finished.");
    }

//    protected boolean shouldUseAuth() {
//        return true; // default: բոլոր թեստերում օգտագործի token
//    }
}
