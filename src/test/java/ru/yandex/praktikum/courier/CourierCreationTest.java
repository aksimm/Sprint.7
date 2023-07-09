package ru.yandex.praktikum.courier;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
public class CourierCreationTest {
    private Courier courier = new Courier();
    private final CourierClient client = new CourierClient();
    private final CourierGenerator generator = new CourierGenerator();
    private String id;

    @Before
    public void createCourier() {
        courier = generator.random();
    }

    @Test
    @DisplayName("Успешное создание курьера")
    @Description("Проверка успешного создания курьера")
    public void courierCreation() {
        ValidatableResponse response = client.create(courier);
        ValidatableResponse loginResponse = client.login(Credentials.from(courier));
        id = loginResponse.extract().path("id").toString();
        response.assertThat().statusCode(201).body("ok", is(true));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("При запросе с повторяющимся логином, код ответа - 409, в теле ответа: 'Этот логин уже используется. Попробуйте другой'")
    public void courierCreationWithExistingLogin() {
        client.create(courier);
        ValidatableResponse response = client.create(courier);
        response.assertThat().statusCode(409).body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("При запросе без логина курьера, код ответа - 400, в теле ответа: 'Недостаточно данных для создания учетной записи'")
    public void courierCreationWithoutLogin() {
        courier.setLogin("");
        ValidatableResponse response = client.create(courier);
        response.assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("При запросе без пароля курьера, код ответа - 400, в теле ответа: 'Недостаточно данных для создания учетной записи'")
    public void courierCreationWithoutPassword() {
        courier.setPassword("");
        ValidatableResponse response = client.create(courier);
        response.assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        if (id != null) {
            client.delete(id);
        }
    }
}

