package com.api.validators;

public class TokenValidator {
    public static void validateJwtFormat(String token) {
        CommonValidator.assertNotNull(token, "token");
        CommonValidator.assertFieldContains(token, ".", "token"); // Բոլոր JWT-ները պետք է պարունակեն '.'

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new AssertionError("❌ Token must follow JWT format (header.payload.signature)");
        }
    }
}
