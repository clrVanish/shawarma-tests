package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoginResponse {

    private final String token;
    private final String type;
    private final String username;
    private final Integer expiresIn;

    @JsonCreator
    public LoginResponse(@JsonProperty("token") String token,
                         @JsonProperty("type") String type,
                         @JsonProperty("username") String username,
                         @JsonProperty("expiresIn") Integer expiresIn) {
        this.token = token;
        this.type = type;
        this.username = username;
        this.expiresIn = expiresIn;
    }

    public String token() { return token; }
    public String type() { return type; }
    public String username() { return username; }
    public Integer expiresIn() { return expiresIn; }
}