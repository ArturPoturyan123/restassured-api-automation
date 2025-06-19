package com.api.base;

import com.api.utils.ConfigReader;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
//import static com.api.utils.ConfigReader.get;

public class TestBase {

    @BeforeMethod
    public void setUp(){

        RestAssured.baseURI = ConfigReader.get("baseUrl");
        System.out.println("✅ Test setup completed");
    }
} 