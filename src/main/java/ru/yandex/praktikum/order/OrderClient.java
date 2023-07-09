package ru.yandex.praktikum.order;

import io.restassured.response.ValidatableResponse;
import io.qameta.allure.Step;
import ru.yandex.praktikum.Client;
public class OrderClient extends Client {
    private final static String ORDERS_API = "/orders";
    private final static String CANCEL_ORDER_API = "/cancel";

    @Step("Создание заказа")
    public ValidatableResponse create(Order order) {
        return  spec()
                .body(order)
                .when()
                .post(ORDERS_API)
                .then().log().all();
    }

    @Step("Список заказов")
    public ValidatableResponse orderList() {
        return  spec()
                .when()
                .get(ORDERS_API)
                .then();
    }

    @Step("Удаление заказа")
    public ValidatableResponse delete(Integer track) {
        return  spec()
                .body(track)
                .when()
                .put(ORDERS_API + CANCEL_ORDER_API)
                .then();
    }
}
