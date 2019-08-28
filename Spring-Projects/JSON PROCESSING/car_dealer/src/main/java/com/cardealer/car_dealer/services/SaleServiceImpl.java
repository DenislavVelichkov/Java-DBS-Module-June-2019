package com.cardealer.car_dealer.services;

import com.cardealer.car_dealer.domain.dtos.views.SaleHistoryDto;
import com.cardealer.car_dealer.domain.dtos.views.SaleWrapperDto;
import com.cardealer.car_dealer.domain.models.*;
import com.cardealer.car_dealer.repository.CarRepository;
import com.cardealer.car_dealer.repository.CustomerRepository;
import com.cardealer.car_dealer.repository.SaleRepository;
import com.cardealer.car_dealer.util.FileUtil;
import com.cardealer.car_dealer.util.Parser;
import com.cardealer.car_dealer.util.ValidatorUtil;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService{

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ValidatorUtil validatorUtil;
    private final Parser gson;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository,
                           CarRepository carRepository,
                           CustomerRepository customerRepository,
                           ValidatorUtil validatorUtil,
                           Parser gson,
                           ModelMapper modelMapper,
                           FileUtil fileUtil) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.validatorUtil = validatorUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importSales() {
        int salesCount = this.getRandomSalesCount() ;

        for (int i = 0; i < salesCount; i++) {
            Sale sale = new Sale();
            sale.setCar(this.getRandomCar());
            sale.setCustomer(this.getRandomCustomer());

            int discount = this.getRandomDiscount();

            if (sale.getCustomer().getYoungDriver()) {
                discount += 5;
            }

            sale.setDiscount(discount);

            if (!this.validatorUtil.isValid(sale)) {
                System.out.println("Something went Wrong");

                continue;
            }

            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public String findAllSaleHistoryByCustomer() {
        TypeMap<Sale, SaleHistoryDto> typeCustomer =
                this.modelMapper.createTypeMap(Sale.class, SaleHistoryDto.class);

        Converter<List<Sale>, Integer> boughtCards =
                src -> src.getSource().size();
        Converter<List<Part>, BigDecimal> spentMoney = this.convertPrice();

        typeCustomer.addMappings(
                m -> m.map(
                        sale -> sale.getCustomer().getName(), SaleHistoryDto::setFullName));
        typeCustomer.addMappings(
                m -> m.using(boughtCards)
                        .map(sale -> sale.getCustomer().getSales(), SaleHistoryDto::setBoughtCars));
        typeCustomer.addMappings(m ->
                m.using(spentMoney)
                        .map(sale -> sale.getCar().getParts(), SaleHistoryDto::setSpentMoney));
        typeCustomer.addMappings(m -> m.map(Sale::getDiscount, SaleHistoryDto::setDiscount));

        List<SaleHistoryDto> sales =
                this.saleRepository.findAllCustomersWithSale()
                .stream()
                .map(typeCustomer::map)
                .collect(Collectors.toList());

        this.fileUtil.exportContentToJsonFile(this.gson.toJsonString(sales),
                System.getProperty("user.dir")
                     + "/src/main/resources/json/export/sale-history.json");

        return this.gson.toJsonString(sales);
    }

    @Override
    public String findAllSalesWithDiscount() {

        Converter<List<Part>, BigDecimal> priceConverter = this.convertPrice();

        TypeMap<Sale, SaleWrapperDto> mapping =
                this.modelMapper.createTypeMap(Sale.class, SaleWrapperDto.class);
        mapping.addMappings(m -> m.map(
                sale -> sale.getCar().getMake(),
                (dest, value) -> dest.getDetailSaleDto().setMake((String) value)));
        mapping.addMappings(m -> m.map(
                sale -> sale.getCar().getModel(),
                (dest, value) -> dest.getDetailSaleDto().setModel((String) value)));
        mapping.addMappings(m -> m.map(
                sale -> sale.getCar().getTravelledDistance(),
                (dest, value) -> dest.getDetailSaleDto().setTravelledDistance((BigInteger) value)));
        mapping.addMappings(m -> m.map(
                sale -> sale.getCustomer().getName(),
                (dest, value) -> dest.getCustomerInfoDto().setCustomerName((String) value)));
        mapping.addMappings(m -> m.map(
                Sale::getDiscount,
                (dest, value) -> dest.getCustomerInfoDto().setDiscount((Double) value)));
        mapping.addMappings(m -> m.using(priceConverter)
                .map(
                        sale -> sale.getCar().getParts(),
                        (dest, value) -> dest.getCustomerInfoDto().setPrice((BigDecimal) value)));
        mapping.addMappings(m -> m.using(priceConverter)
                .map(
                        sale -> sale.getCar().getParts(),
                        (dest, value) -> dest.getCustomerInfoDto().setPriceWithDiscount((BigDecimal) value)));


        List<SaleWrapperDto> saleWrapper =
                this.saleRepository
                        .findAllCustomersWithSale()
                .stream()
                .map(mapping::map)
                .collect(Collectors.toList());

        this.fileUtil.exportContentToJsonFile(this.gson.toJsonString(saleWrapper),
                System.getProperty("user.dir")
                        + "/src/main/resources/json/export/sale-with-discount.json");

        return this.gson.toJsonString(saleWrapper);
    }

    private Converter<List<Part>, BigDecimal> convertPrice() {
        return src -> (src.getSource() == null) ?
                null
                :
                new BigDecimal(
                        src.getSource()
                                .stream()
                                .mapToDouble(value -> Double.valueOf(value.getPrice().toString())).sum());
    }

    private int getRandomDiscount() {
        Random random = new Random();
        int enumIndex = random.nextInt(Discount.values().length - 1) + 1;
        Discount discount = Discount.values()[enumIndex];

        return discount.getValue();
    }

    private Car getRandomCar() {
        Random random = new Random();
        int carIndex = 0;

        while (this.carRepository.findById(carIndex).isEmpty()) {
            carIndex = random.nextInt(this.carRepository.findAll().size() - 1) + 1;
        }

        return this.carRepository.findById(carIndex).get();
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        int customerIndex = 0;

        while (this.customerRepository.findById(customerIndex).isEmpty()) {
            customerIndex = random.nextInt(this.customerRepository.findAll().size() - 1) + 1;
        }

        return this.customerRepository.findById(customerIndex).get();
    }

    private int getRandomSalesCount() {
        Random random = new Random();
        return random.nextInt(30) + 80;
    }
}
