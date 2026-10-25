package clients;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.EventLogResponse;
import utils.Config;

import java.util.List;

import static io.restassured.RestAssured.given;

public class EventsApiClient {

    private final RequestSpecification spec;

    public EventsApiClient() {
        this.spec = given()
                .contentType(ContentType.JSON)
                .baseUri(Config.baseUrl())
                .filter(new AllureRestAssured());
    }

    public Response getEventsRaw(String token, Integer limit, String type) {
        RequestSpecification req = spec;
        if (token != null) {
            req = req.header("Authorization", "Bearer " + token);
        }

        if (limit != null) req = req.queryParam("limit", limit);
        if (type != null) req = req.queryParam("type", type);

        return req.get("/events");
    }

    public List<EventLogResponse> getEvents(String token, Integer limit, String type) {
        return getEventsRaw(token, limit, type)
                .as(new TypeRef<List<EventLogResponse>>() {});
    }
}
