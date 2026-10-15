package clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.LoginResponse;
import models.UserInfo;
import utils.Config;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    private final RequestSpecification spec;

    public AuthApiClient() {
        this.spec = given()
                .contentType(ContentType.JSON)
                .baseUri(Config.baseUrl());
    }

    public Response loginRaw(Map<String, String> body) {
        return spec.body(body).post("/auth/login");
    }

    public LoginResponse login(Map<String, String> body) {
        return loginRaw(body).as(LoginResponse.class);
    }

    public Response getCurrentUserRaw(String token) {
        RequestSpecification req = spec;
        if (token != null) {
            req = req.header("Authorization", "Bearer " + token);
        }
        return req.get("/auth/me");
    }

    public UserInfo getCurrentUser(String token) {
        return getCurrentUserRaw(token).as(UserInfo.class);
    }
}