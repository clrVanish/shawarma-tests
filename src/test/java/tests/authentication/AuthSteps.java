package tests.authentication;

import clients.AuthApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.LoginResponse;
import models.UserInfo;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

public class AuthSteps {

    private static final AuthApiClient api = new AuthApiClient();

    @Step("Вход пользователем {username}")
    public static Response login(String username, String password) {
        return loginWithBody(Map.of("username", username, "password", password));
    }

    // Тело не выводим в отчёт: в нём может быть пароль
    @Step("Вход с произвольным телом запроса")
    public static Response loginWithBody(Map<String, String> body) {
        return api.loginRaw(body);
    }

    @Step("Вход пользователем {username} и получение токена")
    public static String loginAndGetToken(String username, String password) {
        Response response = login(username, password);
        assertTokenIsPresent(response);
        return response.as(LoginResponse.class).token();
    }

    @Step("Запрос данных текущего пользователя")
    public static Response getCurrentUser(String token) {
        return api.getCurrentUserRaw(token);
    }

    @Step("Проверка: в ответе есть непустой токен")
    public static void assertTokenIsPresent(Response response) {
        assertThat(response.as(LoginResponse.class).token())
                .as("Поле token в ответе")
                .isNotBlank();
    }

    @Step("Проверка полей ответа логина")
    public static void assertLoginResponse(Response response) {
        LoginResponse body = response.as(LoginResponse.class);
        assertSoftly(softly -> {
            softly.assertThat(body.token()).as("token").isNotBlank();
            softly.assertThat(body.type()).as("type").isNotBlank();
            softly.assertThat(body.username()).as("username").isNotBlank();
            softly.assertThat(body.expiresIn()).as("expiresIn").isNotNull();
        });
    }

    @Step("Проверка данных текущего пользователя")
    public static void assertCurrentUserInfoPresent(Response response) {
        UserInfo body = response.as(UserInfo.class);
        assertThat(body.username()).as("Поле username").isNotBlank();
        assertThat(body.loggedInAt()).as("Поле loggedInAt").isNotBlank();
    }
}