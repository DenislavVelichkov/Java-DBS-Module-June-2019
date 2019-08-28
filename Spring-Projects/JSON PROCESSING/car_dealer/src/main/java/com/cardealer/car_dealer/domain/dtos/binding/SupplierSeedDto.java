package com.cardealer.car_dealer.domain.dtos.binding;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class SupplierSeedDto implements Serializable {

    @Expose
    private String name;

    @Expose
    private Boolean isImporter;

    public SupplierSeedDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getImporter() {
        return isImporter;
    }

    public void setImporter(Boolean importer) {
        isImporter = importer;
    }
}
