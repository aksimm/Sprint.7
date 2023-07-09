package ru.yandex.praktikum.courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    public Courier generic() {
        return new Courier("Ivan", "qwerty123", "Petrov");
    }
    public Courier random() {
        return new Courier(RandomStringUtils.randomAlphanumeric(5, 10), "qwerty123", "Petrov");
    }
}
