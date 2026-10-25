package clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import static io.restassured.RestAssured.given;

public class EventsApiClient {

    private final RequestSpecBuilder base;

    public EventsApiClient() {
        this.base = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(Config.baseUrl())
                .addFilter(new AllureRestAssured());
    }

    public Response getEventsRaw(String token, Integer limit, String type) {
        RequestSpecification req = given().spec(base.build());
        if (token != null) {
            req = req.header("Authorization", "Bearer " + token);
        }

        if (limit != null) req = req.queryParam("limit", limit);
        if (type != null) req = req.queryParam("type", type);

        return req.get("/events");
    }
}
