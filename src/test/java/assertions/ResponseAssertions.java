package assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.assertj.core.api.Assertions.assertThat;

public final class ResponseAssertions {

    private ResponseAssertions() {
    }

    @Step("Проверка статус-кода: {expected}")
    public static void assertStatusCode(Response response, int expected) {
        assertThat(response.statusCode())
                .as("Статус-код ответа")
                .isEqualTo(expected);
    }
}
