package com.cardealer.car_dealer.domain.dtos.binding;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CarSeedDto implements Serializable {

    @Expose
    private String make;

    @Expose
    private String model;

    @Expose
    private Double travelledDistance;

    public CarSeedDto() {
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance (Double travelledDistance){
        this.travelledDistance = travelledDistance;
    }
}
