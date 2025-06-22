package com.api.validators;

import com.api.models.User;

public class UserValidator {
    public static void validateUserObject(User user) {
        CommonValidator.assertNotNull(user, "user");

        CommonValidator.assertNotNull(user.get_id(), "user id");
        CommonValidator.assertFieldContains(user.get_id(), "", "user id"); // ստուգում ենք դատարկ չէ

        CommonValidator.assertNotNull(user.getAge(), "user age");
        if (user.getAge() <= 0) {
            throw new AssertionError("❌ user age is not valid (<= 0)");
        }

        CommonValidator.assertNotNull(user.get__v(), "user __v");
        if (user.get__v() < 0) {
            throw new AssertionError("❌ user __v is not valid (< 0)");
        }

        CommonValidator.assertNotNull(user.getName(), "user name");
        CommonValidator.assertFieldContains(user.getName(), "", "user name");

        CommonValidator.assertNotNull(user.getAddress(), "user address");
        CommonValidator.assertFieldContains(user.getAddress(), "", "user address");

        CommonValidator.assertNotNull(user.getCreatedAt(), "user createdAt");
        CommonValidator.assertFieldContains(user.getCreatedAt(), "", "user createdAt");

        CommonValidator.assertNotNull(user.getUpdatedAt(), "user updatedAt");
        CommonValidator.assertFieldContains(user.getUpdatedAt(), "", "user updatedAt");
    }
}
