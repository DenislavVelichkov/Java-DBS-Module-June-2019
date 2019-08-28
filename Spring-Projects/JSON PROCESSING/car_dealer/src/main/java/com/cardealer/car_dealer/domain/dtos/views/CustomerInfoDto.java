package com.cardealer.car_dealer.domain.dtos.views;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class CustomerInfoDto implements Serializable {

    @Expose
    private String customerName;

    @Expose
    private Double discount;

    @Expose
    private BigDecimal price;

    @Expose
    private BigDecimal priceWithDiscount;

    private Integer Id;

    public CustomerInfoDto() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.discount = Double.parseDouble(df.format(discount)) / 100;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.price = BigDecimal.valueOf(Double.parseDouble(df.format(price))) ;
    }

    public BigDecimal getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(BigDecimal price) {
        DecimalFormat df = new DecimalFormat("#.##");

        BigDecimal discount =
                BigDecimal.valueOf(
                        this.getDiscount()).divide(BigDecimal.valueOf(100.0))
                        .multiply(new BigDecimal(df.format(price)));

        this.priceWithDiscount = BigDecimal.valueOf(Double.parseDouble(df.format(price.subtract(discount))));
    }
}
