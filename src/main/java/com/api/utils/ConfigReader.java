package com.api.utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final Properties properties = new Properties();

    static {
        try {
            // ✅ Use classloader from thread context — ensures compatibility with Maven
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream input = loader.getResourceAsStream("config.properties");

            if (input == null) {
                throw new RuntimeException("❌ config.properties file not found in classpath!");
            }

            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("❌ Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
