package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.PartSeedDto;
import com.cardealer.car_dealer.domain.models.Part;
import com.cardealer.car_dealer.domain.models.Supplier;
import com.cardealer.car_dealer.repository.PartRepository;
import com.cardealer.car_dealer.repository.SupplierRepository;
import com.cardealer.car_dealer.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;
    private final SupplierRepository supplierRepository;
    private final ValidatorUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public PartServiceImpl(PartRepository partRepository,
                           SupplierRepository supplierRepository,
                           ValidatorUtil validationUtil,
                           ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public void importParts(PartSeedDto[] partSeedDtos) {
        for (PartSeedDto partSeedDto : partSeedDtos) {
            if (!this.validationUtil.isValid(partSeedDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            Part partEntity =
                    this.partRepository
                            .findAllByName(partSeedDto.getName())
                            .orElse(null);

            if (partEntity == null) {
                partEntity = this.modelMapper.map(partSeedDto, Part.class);
                partEntity.setSupplier(this.getRandomSupplier());
                this.partRepository.saveAndFlush(partEntity);
            }

        }
    }

    private Supplier getRandomSupplier() {
        Random random = new Random();
        int randomIndex = random.nextInt((int) (this.supplierRepository.count() - 1)) + 1;

        return this.supplierRepository.findAll().get(randomIndex);
    }
}
