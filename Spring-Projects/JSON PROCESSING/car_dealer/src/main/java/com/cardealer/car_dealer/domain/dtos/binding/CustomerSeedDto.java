package com.cardealer.car_dealer.domain.dtos.binding;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class CustomerSeedDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private String birthDate;

    @Expose
    private Boolean isYoungDriver;

    public CustomerSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getYoungDriver() {
        return isYoungDriver;
    }

    public void setYoungDriver(Boolean youngDriver) {
        isYoungDriver = youngDriver;
    }
}
