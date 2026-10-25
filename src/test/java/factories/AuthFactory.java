package factories;

import models.LoginRequest;
import utils.Config;

import java.util.HashMap;
import java.util.Map;

public final class AuthFactory {

    private AuthFactory() {}

    public static LoginRequest validLogin() {
        return LoginRequest.builder()
                .username(Config.username())
                .password(Config.password())
                .build();
    }

    public static LoginRequest invalidUsername() {
        return validLogin().toBuilder()
                .username(Config.invalidUsername())
                .build();
    }

    public static LoginRequest invalidPassword() {
        return validLogin().toBuilder()
                .password(Config.invalidPassword())
                .build();
    }

    public static LoginRequest invalidBoth() {
        return validLogin().toBuilder()
                .username(Config.invalidUsername())
                .password(Config.invalidPassword())
                .build();
    }

    public static Map<String, String> missingUsername() {
        return Map.of("password", Config.password());
    }

    public static Map<String, String> missingPassword() {
        Map<String, String> body = new HashMap<>();
        body.put("username", Config.username());
        return body;
    }

    public static Map<String, String> emptyFields() {
        Map<String, String> body = new HashMap<>();
        body.put("username", "");
        body.put("password", "");
        return body;
    }
}