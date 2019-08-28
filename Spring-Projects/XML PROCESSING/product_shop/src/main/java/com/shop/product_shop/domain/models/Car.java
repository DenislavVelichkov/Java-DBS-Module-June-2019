package com.shop.product_shop.domain.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "cars")
public class Car extends BaseEntity {
    private String make;
    private String model;
    private Double travelledDistance;
    private Sale sale;
    private Set<Part> parts;

    public Car() {
    }

    @Column(name = "make")
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Column(name = "model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @ManyToMany(targetEntity = Part.class)
    @JoinTable(name = "parts_cars",
        joinColumns = @JoinColumn(name = "part_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id")
    )
    public Set<Part> getParts() {
        return parts;
    }

    public void setParts(Set<Part> parts) {
        this.parts = parts;
    }

    @Column(name = "travelled_distance")
    public Double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(Double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    @OneToOne(targetEntity = Sale.class, mappedBy = "car")
    public Sale getSale() {
        return sale;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }
}
