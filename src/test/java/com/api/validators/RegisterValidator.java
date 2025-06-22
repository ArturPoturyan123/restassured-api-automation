package com.api.validators;

import com.api.models.response.RegisterResponse;
import io.restassured.response.Response;

public class RegisterValidator {


    public static void validateRegisterSuccess(Response response, RegisterResponse body) {
        CommonValidator.assertStatusCode(response, 201);
        CommonValidator.assertNotNull(body.getUser(), "UserId");
        CommonValidator.assertFieldContains(body.getMessage(), "success", "Message");
    }

    public static void validateRegisterFailure(Response response, RegisterResponse body) {
        CommonValidator.assertStatusCode(response, 400);
        CommonValidator.assertFieldContains(body.getMessage(), "already exists", "Message");
    }
}
