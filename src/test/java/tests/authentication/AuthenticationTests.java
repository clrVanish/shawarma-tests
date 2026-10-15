package tests.authentication;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Config;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class AuthenticationTests {

    @Test
    @DisplayName("Вход с верными логином и паролем возвращает 200 и токен")
    void loginWithValidCredentialsReturns200AndToken() {
        Response response = AuthSteps.login(Config.username(), Config.password());

        AuthSteps.assertStatusCode(response, 200);
        AuthSteps.assertTokenIsPresent(response);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loginBodiesWithMissingFields")
    @DisplayName("Вход без обязательных полей возвращает 400")
    void loginWithMissingFieldsReturns400(String caseName, Map<String, String> body) {
        Response response = AuthSteps.loginWithBody(body);

        AuthSteps.assertStatusCode(response, 400);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loginBodiesWithInvalidCredentials")
    @DisplayName("Вход с неверными логином или паролем возвращает 400")
    void loginWithInvalidCredentialsReturns400(String caseName, Map<String, String> body) {
        Response response = AuthSteps.loginWithBody(body);

        AuthSteps.assertStatusCode(response, 400);
    }

    @Test
    @DisplayName("Ответ на вход содержит поля type, username и expiresIn")
    void loginResponseContainsTypeUsernameAndExpiresIn() {
        Response response = AuthSteps.login(Config.username(), Config.password());

        AuthSteps.assertStatusCode(response, 200);
        AuthSteps.assertResponseFields(response, Map.of(
                "type", String.class,
                "username", String.class,
                "expiresIn", Number.class));
    }

    @Test
    @DisplayName("Запрос текущего пользователя с токеном возвращает 200 и данные пользователя")
    void getCurrentUserWithTokenReturns200AndUserInfo() {
        Response loginResponse = AuthSteps.login(Config.username(), Config.password());
        AuthSteps.assertTokenIsPresent(loginResponse);
        String token = loginResponse.jsonPath().getString("token");

        Response response = AuthSteps.getCurrentUser(token);

        AuthSteps.assertStatusCode(response, 200);
        AuthSteps.assertCurrentUserInfoPresent(response);
    }

    @Test
    @DisplayName("Запрос текущего пользователя без токена возвращает 401")
    void getCurrentUserWithoutTokenReturns401() {
        Response response = AuthSteps.getCurrentUser(null);

        AuthSteps.assertStatusCode(response, 401);
    }

    @Test
    @DisplayName("Запрос текущего пользователя с неверным токеном возвращает 401")
    void getCurrentUserWithIncorrectToken() {
        Response response = AuthSteps.getCurrentUser("incorrect token");

        AuthSteps.assertStatusCode(response, 401);
    }

    private static Stream<Arguments> loginBodiesWithMissingFields() {
        Map<String, String> withoutUsername = new HashMap<>();
        withoutUsername.put("password", Config.password());

        Map<String, String> withoutPassword = new HashMap<>();
        withoutPassword.put("username", Config.username());

        Map<String, String> emptyFields = new HashMap<>();
        emptyFields.put("username", "");
        emptyFields.put("password", "");

        return Stream.of(
                Arguments.of("без username", withoutUsername),
                Arguments.of("без password", withoutPassword),
                Arguments.of("пустые username и password", emptyFields));
    }

    private static Stream<Arguments> loginBodiesWithInvalidCredentials() {
        Map<String, String> invalidUsername = new HashMap<>();
        invalidUsername.put("username", Config.invalidUsername());
        invalidUsername.put("password", Config.password());

        Map<String, String> invalidPassword = new HashMap<>();
        invalidPassword.put("username", Config.username());
        invalidPassword.put("password", Config.invalidPassword());

        Map<String, String> invalidBoth = new HashMap<>();
        invalidBoth.put("username", Config.invalidUsername());
        invalidBoth.put("password", Config.invalidPassword());

        return Stream.of(
                Arguments.of("неверный username", invalidUsername),
                Arguments.of("неверный password", invalidPassword),
                Arguments.of("неверные username и password", invalidBoth));
    }
}