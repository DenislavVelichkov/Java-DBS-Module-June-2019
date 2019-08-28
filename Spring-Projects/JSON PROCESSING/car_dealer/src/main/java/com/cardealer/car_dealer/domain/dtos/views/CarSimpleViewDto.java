package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

public class CarSimpleViewDto implements Serializable {

    @Expose
    @SerializedName(value = "Make")
    private String make;

    @Expose
    @SerializedName(value = "Model")
    private String model;

    @Expose
    @SerializedName(value = "TravelledDistance")
    private BigInteger travelledDistance;

    @Expose
    private List<PartSimpleViewDto> parts;

    public CarSimpleViewDto() {
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

    public List<PartSimpleViewDto> getParts() {
        return parts;
    }

    public void setParts(List<PartSimpleViewDto> parts) {
        this.parts = parts;
    }

}
