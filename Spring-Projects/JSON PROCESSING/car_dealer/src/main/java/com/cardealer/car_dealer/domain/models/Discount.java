package com.cardealer.car_dealer.domain.models;

public enum Discount {
    ZERO(0),
    FIVE(5),
    TEN(10),
    FIFTEEN(15),
    TWENTY(20),
    THIRTY(30),
    FORTY(40),
    FIFTY(50);


    private int value;

    Discount(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
