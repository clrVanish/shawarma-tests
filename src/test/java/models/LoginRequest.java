package models;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest {

    private final String username;
    private final String password;

    private LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String username() { return username; }
    public String password() { return password; }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        return map;
    }

    public Builder toBuilder() {
        return new Builder().username(username).password(password);
    }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String username;
        private String password;

        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public LoginRequest build() { return new LoginRequest(username, password); }
    }
}