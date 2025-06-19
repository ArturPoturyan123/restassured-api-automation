package com.api.services;

import com.api.models.request.LoginRequest;
import com.api.utils.ConfigReader;
import com.api.base.BaseService;
import io.restassured.response.Response;

public class AuthService extends BaseService {

    private static final String LOGIN_ENDPOINT = ConfigReader.get("loginEndpoint");

    public Response login(LoginRequest payload) {
        return postRequest(payload, LOGIN_ENDPOINT);

    }
} 