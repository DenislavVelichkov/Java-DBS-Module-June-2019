package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.binding.CustomerSeedDto;
import com.cardealer.car_dealer.domain.dtos.views.CustomerOrderDto;
import com.cardealer.car_dealer.domain.models.Customer;
import com.cardealer.car_dealer.repository.CustomerRepository;
import com.cardealer.car_dealer.util.FileUtil;
import com.cardealer.car_dealer.util.Parser;
import com.cardealer.car_dealer.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidatorUtil validationUtil;
    private final ModelMapper modelMapper;
    private final Parser gson;
    private final FileUtil fileUtil;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ValidatorUtil validationUtil, ModelMapper modelMapper, Parser gson, FileUtil fileUtil) {
        this.customerRepository = customerRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCustomers(CustomerSeedDto[] customerSeedDto) {

        for (CustomerSeedDto customerDto : customerSeedDto) {
            if (!this.validationUtil.isValid(customerDto)) {
                System.out.println("Something went wrong.");

                continue;
            }

            Customer customerEntity =
                    this.customerRepository.findAllByName(customerDto.getName()).orElse(null);
            if (customerEntity == null) {
                customerEntity = this.modelMapper.map(customerDto, Customer.class);
                customerEntity.setBirthDate(
                        LocalDate.parse(
                                customerDto.getBirthDate().substring(
                                        0, customerDto.getBirthDate().lastIndexOf("T"))));
                this.customerRepository.saveAndFlush(customerEntity);
            }
        }
    }

    @Override
    public String orderCustomers() {
        TypeMap<Customer, CustomerOrderDto> typeMap =
                modelMapper.createTypeMap(Customer.class, CustomerOrderDto.class);

        typeMap.addMappings(m -> m.map(Customer::getId, CustomerOrderDto::setId));
        typeMap.addMappings(m -> m.map(Customer::getName, CustomerOrderDto::setName));
        typeMap.addMappings(m -> m.map(Customer::getBirthDate, CustomerOrderDto::setBirthDate));
        typeMap.addMappings(m -> m.map(Customer::getYoungDriver, CustomerOrderDto::setIsYoungDrive));
        typeMap.addMappings(m -> m.skip(CustomerOrderDto::setSales));

        List<CustomerOrderDto> customersOrdered =
                this.customerRepository
                        .findAll()
                        .stream()
                        .sorted((cA, cB) -> {
                            int result =
                                    cB.getBirthDate().compareTo(cA.getBirthDate());
                            return result != 0 ?
                                    result
                                    :
                                    cA.getYoungDriver().compareTo(cB.getYoungDriver());
                        })
                        .map(typeMap::map)
                        .collect(Collectors.toList());

        fileUtil.exportContentToJsonFile(
                this.gson.toJsonString(customersOrdered), System.getProperty("user.dir") +
                                        "/src/main/resources/json/export/customers-order.json");

        return this.gson.toJsonString(customersOrdered);
    }
}
