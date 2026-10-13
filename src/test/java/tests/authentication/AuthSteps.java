package tests.authentication;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.Config;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class AuthSteps {

    private static final String LOGIN_PATH = "/auth/login";
    private static final String CURRENT_USER_PATH = "/auth/me";

    @Step("Вход пользователем {username}")
    public static Response login(String username, String password) {
        return loginWithBody(Map.of("username", username, "password", password));
    }

    // Тело не выводим в отчёт: в нём может быть пароль
    @Step("Вход с произвольным телом запроса")
    public static Response loginWithBody(Map<String, String> body) {
        return given()
                .filter(new AllureRestAssured())
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post(Config.baseUrl() + LOGIN_PATH);
    }

    @Step("Запрос данных текущего пользователя")
    public static Response getCurrentUser(String token) {
        RequestSpecification request = given().filter(new AllureRestAssured());
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        return request.when().get(Config.baseUrl() + CURRENT_USER_PATH);
    }

    @Step("Проверка статус-кода: {expected}")
    public static void assertStatusCode(Response response, int expected) {
        assertThat(response.statusCode())
                .as("Статус-код ответа")
                .isEqualTo(expected);
    }

    @Step("Проверка: в ответе есть непустой токен")
    public static void assertTokenIsPresent(Response response) {
        assertThat(response.jsonPath().getString("token"))
                .as("Поле token в ответе")
                .isNotBlank();
    }

    @Step("Проверка полей ответа: {expectedFields}")
    public static void assertResponseFields(Response response, Map<String, Class<?>> expectedFields) {
        Map<String, Object> body = response.jsonPath().getMap("$");
        expectedFields.forEach((field, type) -> {
            assertThat(body)
                    .as("Поле '%s' есть в ответе", field)
                    .containsKey(field);
            assertThat(body.get(field))
                    .as("Тип поля '%s'", field)
                    .isInstanceOf(type);
        });
    }

    @Step("Проверка данных текущего пользователя")
    public static void assertCurrentUserInfoPresent(Response response) {
        assertThat(response.jsonPath().getString("username"))
                .as("Поле username")
                .isNotBlank();
        assertThat(response.jsonPath().getString("loggedInAt"))
                .as("Поле loggedInAt")
                .isNotBlank();
    }
}