package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CarWrapperDto implements Serializable {

    @Expose
    private CarSimpleViewDto car;

    public CarWrapperDto(CarSimpleViewDto car) {
        this.car = car;
    }
}
