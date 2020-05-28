package org.couragemoon.demo.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EnvProperties {

    private static final Properties properties;

    static {
        properties = new Properties();
        InputStream inputStream = EnvProperties.class.getResourceAsStream("/env.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static int getServerPort() {
        return Integer.parseInt(properties.getProperty("server.port"));
    }
}
