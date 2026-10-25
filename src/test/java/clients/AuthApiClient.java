package clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApiClient {

    private final RequestSpecBuilder base;

    public AuthApiClient() {
        this.base = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(Config.baseUrl())
                .addFilter(new AllureRestAssured());
    }

    public Response loginRaw(Map<String, String> body) {
        return given().spec(base.build()).body(body).post("/auth/login");
    }

    public Response getCurrentUserRaw(String token) {
        RequestSpecification req = given().spec(base.build());
        if (token != null) {
            req = req.header("Authorization", "Bearer " + token);
        }
        return req.get("/auth/me");
    }

}