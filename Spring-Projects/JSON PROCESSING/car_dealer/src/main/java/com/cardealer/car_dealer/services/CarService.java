package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.CarSeedDto;

import java.io.IOException;

public interface CarService {

    void importCars(CarSeedDto[] carSeedDto);

    String findAllCarsFromMakeToyota(String make);

    String findAllCarsAlongWithTheirParts() throws IOException;
}
