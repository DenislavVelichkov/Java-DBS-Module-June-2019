package com.cardealer.car_dealer.domain.dtos.binding;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SaleSeedDto implements Serializable {

    @Expose
    private Integer discount;

    public SaleSeedDto() {
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
