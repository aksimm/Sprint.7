package ru.yandex.praktikum.courier;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import ru.yandex.praktikum.Client;

public class CourierClient extends Client {
    private final static String COURIER_API = "/courier";
    private final static String LOGIN_API = "/login";

    @Step("Создание курьера")
    public ValidatableResponse create(Courier courier) {
        return spec()
                .body(courier)
                .when()
                .post(COURIER_API)
                .then().log().all();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse login(Credentials credentials) {
        return spec()
                .body(credentials)
                .when()
                .post(COURIER_API + LOGIN_API)
                .then().log().all();
    }

    @Step("Удаление курьера")
    public ValidatableResponse delete(String id) {
        return spec()
                .when()
                .delete(COURIER_API + LOGIN_API + id)
                .then();
    }
}

