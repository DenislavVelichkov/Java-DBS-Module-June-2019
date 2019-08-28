package com.cardealer.car_dealer.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

@Component
public class GsonParser implements Parser {
    private static Gson gson;

    static {
        gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
    }

    @Override
    public String toJsonString(Object obj) {
        return gson.toJson(obj);
    }

    @Override
    public <T> T exportFileContent(String str, Class<T> klass) {
        return gson.fromJson(str, klass);
    }
}
