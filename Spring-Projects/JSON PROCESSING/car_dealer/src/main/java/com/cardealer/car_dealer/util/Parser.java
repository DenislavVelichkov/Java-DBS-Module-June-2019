package com.cardealer.car_dealer.util;

public interface Parser {
    String toJsonString(Object obj);
    <T> T exportFileContent(String str, Class<T> klass);
}
