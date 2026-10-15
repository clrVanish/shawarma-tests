package models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserInfo {

    private final String username;
    private final String loggedInAt;

    @JsonCreator
    public UserInfo(@JsonProperty("username") String username,
                    @JsonProperty("loggedInAt") String loggedInAt) {
        this.username = username;
        this.loggedInAt = loggedInAt;
    }

    public String username() { return username; }
    public String loggedInAt() { return loggedInAt; }
}