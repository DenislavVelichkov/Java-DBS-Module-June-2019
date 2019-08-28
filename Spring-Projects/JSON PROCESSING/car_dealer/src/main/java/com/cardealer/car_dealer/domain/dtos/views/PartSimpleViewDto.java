package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.math.BigDecimal;

public class PartSimpleViewDto implements Serializable {

    @Expose
    @SerializedName(value = "Name")
    private String name;

    @Expose
    @SerializedName(value = "Price")
    private BigDecimal price;

    public PartSimpleViewDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
