package com.api.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {


    private static final Properties properties = new Properties();


    static {
        try {
            String env = System.getProperty("env", "dev");
            String configFileName = String.format("config/%s.properties", env);


            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream(configFileName);

            if (input == null) {
                throw new RuntimeException("❌ Config file not found: " + configFileName);
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load configuration", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

}
