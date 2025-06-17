package com.restassured.infrastructure.http;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class BaseService {

    protected RequestSpecification requestSpec() {
        return RestAssured
                .given()
                .contentType(ContentType.JSON);
    }

    protected Response postRequest(Object payload, String endpoint) {
        return requestSpec()
                .body(payload)
                .post(endpoint);
    }

    protected Response getRequest(String endpoint) {
        return requestSpec()
                .get(endpoint);
    }

    protected Response putRequest(Object payload, String endpoint) {
        return requestSpec()
                .body(payload)
                .put(endpoint);
    }

    protected Response deleteRequest(String endpoint) {
        return requestSpec()
                .delete(endpoint);
    }
} 