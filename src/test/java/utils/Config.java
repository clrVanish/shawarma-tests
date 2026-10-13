package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final String DEFAULT_BASE_URL = "https://shawarma.threadqa.ru/api/v1";
    private static final Properties PROPS = load();

    private Config() {
    }

    private static Properties load() {
        Properties props = new Properties();
        try (InputStream in = Config.class.getResourceAsStream("/config.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot read /config.properties from classpath", e);
        }
        return props;
    }

    // Приоритет: system property -> env -> config.properties
    private static String get(String key, String envKey) {
        String value = firstNonBlank(
                System.getProperty(key),
                System.getProperty("shawarma." + key),
                System.getenv(envKey),
                PROPS.getProperty(key));
        return value == null ? null : value.trim();
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static String required(String key, String envKey) {
        String value = get(key, envKey);
        if (value == null) {
            throw new IllegalStateException(
                    "Missing '" + key + "'. Set -D" + key + ", env " + envKey
                            + " or key in src/test/resources/config.properties");
        }
        return value;
    }

    public static String baseUrl() {
        String value = get("baseUrl", "SHAWARMA_BASE_URL");
        return value != null ? value : DEFAULT_BASE_URL;
    }

    public static String username() {
        return required("username", "SHAWARMA_USERNAME");
    }

    public static String password() {
        return required("password", "SHAWARMA_PASSWORD");
    }

    public static String invalidUsername() {
        return required("invalidUsername", "SHAWARMA_INVALID_USERNAME");
    }

    public static String invalidPassword() {
        return required("invalidPassword", "SHAWARMA_INVALID_PASSWORD");
    }
}