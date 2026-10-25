package tests.authentication;

import clients.AuthApiClient;
import factories.AuthFactory;
import io.restassured.response.Response;
import models.LoginResponse;
import models.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import utils.Config;

import java.util.Map;
import java.util.stream.Stream;

import static assertions.ResponseAssertions.assertStatusCode;

public class AuthenticationTests {

    private final AuthApiClient api = new AuthApiClient();

    @Test
    @DisplayName("Вход с верными логином и паролем возвращает 200 и токен")
    void loginWithValidCredentialsReturns200AndToken() {
        Response response = AuthSteps.login(Config.username(), Config.password());

        assertStatusCode(response, 200);
        AuthSteps.assertTokenIsPresent(response);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loginBodiesWithMissingFields")
    @DisplayName("Вход без обязательных полей возвращает 400")
    void loginWithMissingFieldsReturns400(String caseName, Map<String, String> body) {
        Response response = api.loginRaw(body);

        assertStatusCode(response, 400);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("loginBodiesWithInvalidCredentials")
    @DisplayName("Вход с неверными логином или паролем возвращает 400")
    void loginWithInvalidCredentialsReturns400(String caseName, Map<String, String> body) {
        Response response = api.loginRaw(body);

        assertStatusCode(response, 400);
    }

    @Test
    @DisplayName("Ответ на вход содержит поля type, username и expiresIn")
    void loginResponseContainsTypeUsernameAndExpiresIn() {
        Response response = AuthSteps.login(Config.username(), Config.password());

        assertStatusCode(response, 200);
        AuthSteps.assertLoginResponse(response);
    }

    @Test
    @DisplayName("Запрос текущего пользователя с токеном возвращает 200 и данные пользователя")
    void getCurrentUserWithTokenReturns200AndUserInfo() {
        Response loginResponse = AuthSteps.login(Config.username(), Config.password());
        AuthSteps.assertTokenIsPresent(loginResponse);
        String token = loginResponse.as(LoginResponse.class).token();

        Response response = AuthSteps.getCurrentUser(token);

        assertStatusCode(response, 200);
        AuthSteps.assertCurrentUserInfoPresent(response);
    }

    @Test
    @DisplayName("Запрос текущего пользователя без токена возвращает 401")
    void getCurrentUserWithoutTokenReturns401() {
        Response response = api.getCurrentUserRaw(null);

        assertStatusCode(response, 401);
    }

    @Test
    @DisplayName("Запрос текущего пользователя с неверным токеном возвращает 401")
    void getCurrentUserWithIncorrectToken() {
        Response response = api.getCurrentUserRaw("incorrect token");

        assertStatusCode(response, 401);
    }

    private static Stream<Arguments> loginBodiesWithMissingFields() {
        return Stream.of(
                Arguments.of("без username", AuthFactory.missingUsername()),
                Arguments.of("без password", AuthFactory.missingPassword()),
                Arguments.of("пустые username и password", AuthFactory.emptyFields()));
    }

    private static Stream<Arguments> loginBodiesWithInvalidCredentials() {
        return Stream.of(
                Arguments.of("неверный username", AuthFactory.invalidUsername().toMap()),
                Arguments.of("неверный password", AuthFactory.invalidPassword().toMap()),
                Arguments.of("неверные username и password", AuthFactory.invalidBoth().toMap()));
    }
}