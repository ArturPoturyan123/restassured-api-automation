package com.restassured.infrastructure.services;

import com.restassured.domain.entities.request.LoginRequest;
import com.restassured.infrastructure.config.ConfigReader;
import com.restassured.infrastructure.base.BaseService;
import io.restassured.response.Response;

public class AuthService extends BaseService {

    private static final String LOGIN_ENDPOINT = ConfigReader.get("loginEndpoint");

    public Response login(LoginRequest payload) {
        return postRequest(payload, LOGIN_ENDPOINT);

    }
} 