package com.restassured.tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import static com.restassured.infrastructure.config.ConfigReader.get;

public class TestBase {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = get("baseUrl");
        System.out.println("✅ Test setup completed");
    }
} 