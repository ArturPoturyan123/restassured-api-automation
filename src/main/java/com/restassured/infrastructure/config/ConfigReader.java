package com.restassured.infrastructure.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties = new Properties();
            properties.load(file);
            file.close();
        } catch (IOException e) {
            throw new RuntimeException("⚠️ Couldn't load config.properties file", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
} 