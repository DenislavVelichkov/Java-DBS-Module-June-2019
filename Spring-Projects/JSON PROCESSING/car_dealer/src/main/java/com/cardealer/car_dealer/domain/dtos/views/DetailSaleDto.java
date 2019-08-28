package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;

public class DetailSaleDto implements Serializable {

    @Expose
    @SerializedName(value = "Make")
    private String make;

    @Expose
    @SerializedName(value = "Model")
    private String model;

    @Expose
    @SerializedName(value = "TravelledDistance")
    private BigInteger travelledDistance;

    public DetailSaleDto() {
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

    public BigInteger getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(BigInteger travelledDistance) {
        this.travelledDistance = travelledDistance;
    }
}
