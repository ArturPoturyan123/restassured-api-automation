package com.api.utils;

import com.api.models.request.LoginRequest;
import com.api.models.response.LoginResponse;
import com.api.services.AuthService;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {
            AuthService authService = new AuthService();
            LoginRequest request = new LoginRequest();
            request.setEmail(ConfigReader.get("loginEmail"));
            request.setPassword(ConfigReader.get("loginPassword"));

            LoginResponse response = authService.login(request).as(LoginResponse.class);
            token = response.getToken();
            System.out.println("✅ Token obtained successfully");

        }
        return token;
    }
    // Optional: Reset token if needed
    public static void resetToken() {
        token = null;
    }
}
