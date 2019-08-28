package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class SaleHistoryDto implements Serializable {

    @Expose
    private String fullName;

    @Expose
    private Integer boughtCars;

    @Expose
    private BigDecimal spentMoney;

    private Integer discount;

    public SaleHistoryDto() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public BigDecimal getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(BigDecimal spentMoney) {
        DecimalFormat df = new DecimalFormat("#.##");

        BigDecimal discount =
                new BigDecimal(
                        this.getDiscount()).divide(BigDecimal.valueOf(100.0))
                                            .multiply(new BigDecimal(df.format(spentMoney)));

        this.spentMoney = new BigDecimal(df.format(spentMoney.subtract(discount)));
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
