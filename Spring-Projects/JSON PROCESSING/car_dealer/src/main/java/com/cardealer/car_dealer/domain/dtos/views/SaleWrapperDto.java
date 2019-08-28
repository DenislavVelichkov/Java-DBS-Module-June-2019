package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SaleWrapperDto implements Serializable {

    @Expose
    @SerializedName(value = "car")
    private DetailSaleDto detailSaleDto;

    @Expose
    @SerializedName(value = "customer")
    private CustomerInfoDto customerInfoDto;

    public SaleWrapperDto() {
    }

    public SaleWrapperDto(CustomerInfoDto customerInfoDto) {
        this.customerInfoDto = customerInfoDto;
    }

    public DetailSaleDto getDetailSaleDto() {
        return detailSaleDto;
    }

    public void setDetailSaleDto(DetailSaleDto detailSaleDto) {
        this.detailSaleDto = detailSaleDto;
    }

    public CustomerInfoDto getCustomerInfoDto() {
        return customerInfoDto;
    }

    public void setCustomerInfoDto(CustomerInfoDto customerInfoDto) {
        this.customerInfoDto = customerInfoDto;
    }
}
