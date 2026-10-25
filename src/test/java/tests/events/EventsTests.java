package tests.events;

import clients.EventsApiClient;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.EventLogResponse;
import models.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.authentication.AuthSteps;
import utils.Config;

import java.util.List;

public class EventsTests {

    private final EventsApiClient api = new EventsApiClient();

    @Test
    @DisplayName("Без параметров возвращает 200 до 20 записей")
    void getEventsWithoutQueryParams() {
        Response loginResponse = AuthSteps.login(Config.username(), Config.password());

        AuthSteps.assertTokenIsPresent(loginResponse);
        String token = loginResponse.as(LoginResponse.class).token();

        Response response = EventsSteps.getEvents(token, null, null);

        EventsSteps.assertStatusCode(response, 200);
        EventsSteps.assertEventsResponse(response);

        EventsSteps.assertEventCount(response, null);

    }

}
