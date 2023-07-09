package ru.yandex.praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CourierAuthorizationTest {
    private Courier courier = new Courier();
    private final CourierClient client = new CourierClient();
    private final CourierGenerator generator = new CourierGenerator();
    private String id;

    @Before
    public void setUp() {
        courier = generator.random();
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка успешной авторизации курьера")
    public void courierAuthorization() {
        ValidatableResponse response = client.create(courier);
        ValidatableResponse loginResponse = client.login(Credentials.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без логина")
    @Description("При запросе авторизации без логина курьера, код ответа - 400, в теле ответа: 'Недостаточно данных для входа'")
    public void courierAuthorizationWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = client.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тестирование авторизации курьера без пароля")
    @Description("При запросе авторизации без пароля курьера, код ответа - 400, в теле ответа: 'Недостаточно данных для входа'")
    public void courierAuthorizationWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = client.login(Credentials.from(courier));
        response.statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            client.delete(id);
        }
    }
}

