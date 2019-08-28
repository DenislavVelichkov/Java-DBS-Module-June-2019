package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.PartSeedDto;

public interface PartService {

    void importParts(PartSeedDto[] partSeedDtos);
}
