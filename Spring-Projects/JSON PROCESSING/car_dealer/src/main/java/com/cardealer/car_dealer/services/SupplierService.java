package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.SupplierSeedDto;

public interface SupplierService {

    void importSuppliers(SupplierSeedDto[] supplierSeedDtos);

    String findAllNonImporters();
}
