package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.CustomerSeedDto;

public interface CustomerService {

    void importCustomers(CustomerSeedDto[] customerSeedDto);

    String orderCustomers();
}
