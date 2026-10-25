package tests.events;

import clients.EventsApiClient;
import io.qameta.allure.Step;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import models.EventLogResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class EventsSteps {

    private static final EventsApiClient api = new EventsApiClient();

    @Step("Получаем события")
    public static Response getEvents(String token, Integer limit, String type) {
        return api.getEventsRaw(token, limit, type);
    }

    @Step("Проверка статус кода: {expected}")
    public static void assertStatusCode(Response response, int expected) {
        assertThat(response.statusCode())
                .as("Статус-код ответа")
                .isEqualTo(expected);
    }

    @Step("Проверка полей ответа событий")
    public static void assertEventsResponse(Response response) {
        List<EventLogResponse> events = response.as(new TypeRef<List<EventLogResponse>>() {});
        assertThat(events).as("Список событий").isNotEmpty();

        EventLogResponse first = events.get(0);
        assertSoftly(softly -> {
            softly.assertThat(first.id()).as("id").isNotNull();
            softly.assertThat(first.type()).as("type").isNotBlank();
            softly.assertThat(first.payload()).as("payload").isNotNull();
            softly.assertThat(first.ts()).as("ts").isNotBlank();
        });
    }

    @Step("Проверка количества полей")
    public static void assertEventCount(Response response, Integer expectedLimit) {
        List<EventLogResponse> events = response.as(new TypeRef<List<EventLogResponse>>() {});
        int actual = events.size();

        if (expectedLimit != null) {
            assertThat(actual).as("Количество событий").isEqualTo(expectedLimit);
        } else {
            assertThat(actual).as("Количество событий (дефолт <= 20").isLessThanOrEqualTo(20);
        }
    }

}
