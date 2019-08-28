package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class NotImporterDto implements Serializable {

    @Expose
    @SerializedName(value = "Id")
    private Integer id;

    @Expose
    @SerializedName(value = "Name")
    private String name;

    @Expose
    private Integer partsCount;

    public NotImporterDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPartsCount() {
        return partsCount;
    }

    public void setPartsCount(Integer partsCount) {
        this.partsCount = partsCount;
    }
}
