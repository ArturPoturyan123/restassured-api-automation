package com.api.validators;

import io.restassured.response.Response;
import org.testng.Assert;

public class CommonValidator {


    public static void assertStatusCode(Response response, int expected) {

        Assert.assertEquals(response.getStatusCode(), expected, "❌ Status code mismatch");
    }

    public static void assertContentType(Response response, String expected) {
        Assert.assertEquals(response.getContentType(), expected, "❌ Content-Type mismatch");
    }

    public static void assertFieldEquals(String actual, String expected, String fieldName) {
        Assert.assertEquals(actual, expected, "❌ " + fieldName + " mismatch");
    }

    public static void assertFieldContains(String actual, String expected, String fieldName) {
        Assert.assertTrue(actual.contains(expected), "❌ " + fieldName + " should contain: " + expected);
    }

    public static void assertNotNull(Object actual, String fieldName) {
        Assert.assertNotNull(actual, "❌ " + fieldName + " should not be null");
    }

}
