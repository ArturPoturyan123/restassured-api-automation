package com.api.validators;

import com.api.models.response.LoginResponse;
import io.restassured.response.Response;

public class LoginValidator {


    public static void validateLoginSuccess(Response response, LoginResponse body) {
        CommonValidator.assertStatusCode(response, 200);
        CommonValidator.assertNotNull(body.getToken(), "Token");
        CommonValidator.assertFieldContains(body.getMessage(), "success", "Message");
    }

    public static void validateLoginFailure(Response response, LoginResponse body) {
        CommonValidator.assertStatusCode(response, 400);
        CommonValidator.assertFieldContains(body.getMessage(), "failed", "Message");
    }
}
