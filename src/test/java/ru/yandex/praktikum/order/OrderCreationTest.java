package ru.yandex.praktikum.order;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    private final OrderClient orderClient = new OrderClient();
    private Integer track;

    public OrderCreationTest(String firstName, String lastName, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Проверка выбора цвета самоката при заказе. Тестовые данные: {0}, {1}, {2}, {3}, {4}")
    public static Object[][] getParams() {
        return new Object[][]{
                {"Иван", "Иванов", "ул. Мира, 1", "Черкизовская", "+79123456789", 2, "2023-07-12", "Не звонить в домофон", new String[]{"GREY"}},
                {"Иван", "Иванов", "ул. Мира, 1", "Черкизовская", "+79123456789", 2, "2023-07-12", "Не звонить в домофон", new String[]{"BLACK"}},
                {"Иван", "Иванов", "ул. Мира, 1", "Черкизовская", "+79123456789", 2, "2023-07-12", "Не звонить в домофон", new String[]{"BLACK", "GREY"}},
                {"Иван", "Иванов", "ул. Мира, 1", "Черкизовская", "+79123456789", 2, "2023-07-12", "Не звонить в домофон", new String[]{}},
                {"Иван", "Иванов", "ул. Мира, 1", "Черкизовская", "+79123456789", 2, "2023-07-12", "Не звонить в домофон", null}
        };
    }

    @Test
    @DisplayName("Заказ самоката")
    public void orderCreationTest() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        ValidatableResponse response = orderClient.create(order);
        response.assertThat().log().all().statusCode(201).body("track", is(notNullValue()));
        track = response.extract().path("track");
    }

    @After
    public void deleteOrder() {
        orderClient.delete(track);
    }
}

