package com.restassured.infrastructure.services;

import com.restassured.domain.entities.LoginRequest;
import com.restassured.infrastructure.config.ConfigReader;
import com.restassured.infrastructure.http.BaseService;
import io.restassured.response.Response;

public class AuthService extends BaseService {

    private static final String LOGIN_ENDPOINT = ConfigReader.get("LoginEndpoint");

    public Response login(LoginRequest payload) {
        return postRequest(payload, LOGIN_ENDPOINT);
        
    }
} 