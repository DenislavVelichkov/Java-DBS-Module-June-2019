package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.SupplierSeedDto;
import com.cardealer.car_dealer.domain.dtos.views.NotImporterDto;
import com.cardealer.car_dealer.domain.models.Part;
import com.cardealer.car_dealer.domain.models.Supplier;
import com.cardealer.car_dealer.repository.SupplierRepository;
import com.cardealer.car_dealer.util.FileUtil;
import com.cardealer.car_dealer.util.Parser;
import com.cardealer.car_dealer.util.ValidatorUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final Parser gson;
    private final FileUtil fileUtil;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository,
                               ValidatorUtil validatorUtil,
                               ModelMapper modelMapper,
                               Parser gson,
                               FileUtil fileUtil) {
        this.supplierRepository = supplierRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importSuppliers(SupplierSeedDto[] supplierSeedDtos) {
        for (SupplierSeedDto supplierSeedDto : supplierSeedDtos) {
            if (!this.validatorUtil.isValid(supplierSeedDto)) {
                System.out.println("Something went wrong");

                continue;
            }

            Supplier supplierEntity =
                    this.supplierRepository
                            .findAllByName(supplierSeedDto.getName())
                            .orElse(null);

            if (supplierEntity == null) {
                supplierEntity = this.modelMapper.map(supplierSeedDto, Supplier.class);
                this.supplierRepository.saveAndFlush(supplierEntity);
            }

        }
    }

    @Override
    public String findAllNonImporters() {
        TypeMap<Supplier, NotImporterDto> typeMap =
                this.modelMapper.createTypeMap(Supplier.class, NotImporterDto.class);

        Converter<List<Part>, Integer> partsCount =
                src -> src.getSource() == null ? null : src.getSource().size();

        typeMap.addMappings(m -> m.map(
                Supplier::getId, NotImporterDto::setId));
        typeMap.addMappings(m -> m.map(
                Supplier::getName, NotImporterDto::setName));
        typeMap.addMappings(m -> m.using(partsCount)
                .map(Supplier::getParts, NotImporterDto::setPartsCount));

        List<NotImporterDto> nonImporters = this.supplierRepository
                .findAllNonImporters()
                .stream()
                .map(typeMap::map)
                .collect(Collectors.toList());

        this.fileUtil.exportContentToJsonFile(
                this.gson.toJsonString(nonImporters),
                                System.getProperty("user.dir")
                                   + "/src/main/resources/json/export/non-importers.json");

        return this.gson.toJsonString(nonImporters);
    }
}
