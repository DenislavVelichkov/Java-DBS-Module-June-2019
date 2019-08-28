package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.CarSeedDto;
import com.cardealer.car_dealer.domain.dtos.views.CarByMakeDto;
import com.cardealer.car_dealer.domain.dtos.views.CarSimpleViewDto;
import com.cardealer.car_dealer.domain.dtos.views.CarWrapperDto;
import com.cardealer.car_dealer.domain.dtos.views.PartSimpleViewDto;
import com.cardealer.car_dealer.domain.models.Car;
import com.cardealer.car_dealer.domain.models.Part;
import com.cardealer.car_dealer.repository.CarRepository;
import com.cardealer.car_dealer.repository.PartRepository;
import com.cardealer.car_dealer.util.FileUtil;
import com.cardealer.car_dealer.util.Parser;
import com.cardealer.car_dealer.util.ValidatorUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final ValidatorUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Parser gson;
    private final FileUtil fileUtil;

    @Autowired
    public CarServiceImpl(CarRepository carRepository,
                          PartRepository partRepository,
                          ValidatorUtil validatorUtil,
                          ModelMapper modelMapper,
                          Parser gson,
                          FileUtil fileUtil) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.validationUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCars(CarSeedDto[] carSeedDtos) {
        for (CarSeedDto carSeedDto : carSeedDtos) {
            if (!this.validationUtil.isValid(carSeedDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            Car carEntity =
                    this.carRepository
                            .findAllByTravelledDistance(carSeedDto.getTravelledDistance())
                            .orElse(null);

            if (carEntity == null) {
                carEntity = this.modelMapper.map(carSeedDto, Car.class);
                carEntity.setParts(this.getRandomParts());
                this.carRepository.saveAndFlush(carEntity);
            }
        }
    }

    @Override
    public String findAllCarsFromMakeToyota(String make) {
        List<CarByMakeDto> cars =
                this.carRepository
                        .findAllByMakeOrderByModelTravelledDistanceDesc(make)
                        .stream()
                        .map(car -> this.modelMapper.map(car, CarByMakeDto.class))
                        .collect(Collectors.toList());
        fileUtil.exportContentToJsonFile(this.gson.toJsonString(cars), System.getProperty("user.dir")
                                                + "/src/main/resources/json/export/cars-by-make.json");

        return this.gson.toJsonString(cars);
    }

    @Override
    public String findAllCarsAlongWithTheirParts() {
        TypeMap<Part, PartSimpleViewDto> typePart =
                this.modelMapper.createTypeMap(Part.class, PartSimpleViewDto.class);

        typePart.addMappings(m -> m.map(Part::getName, PartSimpleViewDto::setName));
        typePart.addMappings(m -> m.map(Part::getPrice, PartSimpleViewDto::setPrice));

        Converter<List<Part>, List<PartSimpleViewDto>> partsToSimpleView =
                src -> src.getSource() == null ?
                        null
                        :
                        src.getSource()
                        .stream()
                                .map(typePart::map)
                                .collect(Collectors.toList());

        TypeMap<Car, CarSimpleViewDto> typeCar =
                this.modelMapper.createTypeMap(Car.class, CarSimpleViewDto.class);

        typeCar.addMappings(m -> m.map(
                Car::getMake, CarSimpleViewDto::setMake));
        typeCar.addMappings(m -> m.map(
                Car::getModel, CarSimpleViewDto::setModel));
        typeCar.addMappings(m -> m.map(
                Car::getTravelledDistance, CarSimpleViewDto::setTravelledDistance));
        typeCar.addMappings(m -> m.using(partsToSimpleView)
                .map(Car::getParts, CarSimpleViewDto::setParts));

        List<CarSimpleViewDto> cars =
                this.carRepository.findAll()
                .stream()
                .map(typeCar::map)
                .collect(Collectors.toList());

        List<CarWrapperDto> carWrapperDtos = new ArrayList<>();
        for (CarSimpleViewDto car : cars) {
            CarWrapperDto carWrapperDto = new CarWrapperDto(car);
            carWrapperDtos.add(carWrapperDto);
        }

        this.fileUtil.exportContentToJsonFile(this.gson.toJsonString(carWrapperDtos),
                                         System.getProperty("user.dir")
                                              + "/src/main/resources/json/export/cars-with-parts.json");

        return this.gson.toJsonString(carWrapperDtos);
    }

    private List<Part> getRandomParts() {
        List<Part> parts = new ArrayList<>();
        Random rnd = new Random();

        List<Part> partEntities = this.partRepository.findAll();

        int length = rnd.nextInt(10) + 10;

        for (int i = 0; i < length; i++) {
            int partIndex = rnd.nextInt((int) (this.partRepository.count() - 1)) + 1 ;
            parts.add(partEntities.get(partIndex));
        }

        return parts;
    }
}
