package ru.yandex.praktikum.order;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {
    private final OrderClient orderClient = new OrderClient();

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка успешного выполнения запроса")
    public void orderListTest() {
        ValidatableResponse response = orderClient.orderList();
        response.statusCode(200).and().body("orders", notNullValue());
    }
}
