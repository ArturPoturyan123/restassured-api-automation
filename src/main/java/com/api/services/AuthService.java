package com.api.services;

import com.api.base.BaseService;
import com.api.models.request.LoginRequest;
import com.api.models.request.RegisterRequest;
import io.restassured.response.Response;
import com.api.utils.ConfigReader;

public class AuthService extends BaseService {

    private static final String LOGIN_ENDPOINT = ConfigReader.get("loginEndpoint");
    private static final String REGISTER_ENDPOINT = ConfigReader.get("registerEndpoint");

    public Response login(LoginRequest payload) {
        return postRequest(payload, LOGIN_ENDPOINT);
    }

    public Response register(RegisterRequest payload) {
        return postRequest(payload, REGISTER_ENDPOINT);
    }
}
