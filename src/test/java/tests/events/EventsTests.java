package tests.events;

import clients.EventsApiClient;
import io.restassured.response.Response;
import models.LoginResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tests.authentication.AuthSteps;
import utils.Config;

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

    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {1, 20, 100})
    @DisplayName("Валидный limit возвращает 200")
    void getPositiveEventsWithQueryParams(int limit) {
        Response loginResponse = AuthSteps.login(Config.username(), Config.password());

        AuthSteps.assertTokenIsPresent(loginResponse);
        String token = loginResponse.as(LoginResponse.class).token();

        Response response = EventsSteps.getEvents(token, limit, null);

        EventsSteps.assertStatusCode(response, 200);
        EventsSteps.assertEventCount(response, limit);
    }

    @ParameterizedTest(name = "{0}")
    @ValueSource(ints = {-1, 0, 101})
    @DisplayName("Невалидный limit возвращает 500")
    void getNegativeEventsWithQueryParams(int limit) {
        Response loginResponse = AuthSteps.login(Config.username(), Config.password());

        AuthSteps.assertTokenIsPresent(loginResponse);
        String token = loginResponse.as(LoginResponse.class).token();

        Response response = EventsSteps.getEvents(token, limit, null);

        EventsSteps.assertStatusCode(response, 500);
    }

}
