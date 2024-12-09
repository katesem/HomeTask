package com.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Class for loading configuration properties from the config.properties file
 * and making them available for the rest of application.
 **/

public class ConfigLoader {

    private static final Properties properties = loadProperties();

    /**
     * Method for loading property values from the config.properties file
     **/
    private static Properties loadProperties() {
        Properties props = new Properties();
        try (FileInputStream stream = new FileInputStream("src/main/resources/config.properties")) {
            props.load(stream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading configuration properties", e);
        }
        return props;
    }

    /**
     * Method for extracting property value by its key
     **/
    public static String get(String key) {
        return properties.getProperty(key);
    }
}