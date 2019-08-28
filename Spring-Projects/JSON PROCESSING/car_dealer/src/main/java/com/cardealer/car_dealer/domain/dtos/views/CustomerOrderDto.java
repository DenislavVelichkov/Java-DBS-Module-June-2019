package com.cardealer.car_dealer.domain.dtos.views;

import com.cardealer.car_dealer.domain.models.Sale;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomerOrderDto implements Serializable {

    @Expose
    @SerializedName(value = "Id")
    private Integer id;

    @Expose
    @SerializedName(value = "Name")
    private String name;

    @Expose
    @SerializedName(value = "BirthDate")
    private String birthDate;

    @Expose
    @SerializedName(value = "IsYoungDriver")
    private boolean isYoungDrive;

    @Expose
    @SerializedName(value = "Sales")
    private List<Sale> sales;

    public CustomerOrderDto() {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsYoungDrive() {
        return isYoungDrive;
    }

    public void setIsYoungDrive(boolean isYoungDrive) {
        this.isYoungDrive = isYoungDrive;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public void setSales(List<Sale> sales) {
        this.sales = sales;
    }
}
