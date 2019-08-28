package com.cardealer.car_dealer.web.controller;

import com.cardealer.car_dealer.domain.dtos.binding.CarSeedDto;
import com.cardealer.car_dealer.domain.dtos.binding.CustomerSeedDto;
import com.cardealer.car_dealer.domain.dtos.binding.PartSeedDto;
import com.cardealer.car_dealer.domain.dtos.binding.SupplierSeedDto;
import com.cardealer.car_dealer.services.*;
import com.cardealer.car_dealer.util.FileUtil;
import com.cardealer.car_dealer.util.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class AppController implements CommandLineRunner {

    private final static String SUPPLIERS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/suppliers.json";
    private final static String PARTS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/parts.json";
    private final static String CARS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/cars.json";
    private final static String CUSTOMERS_JSON_FILE_PATH =
            System.getProperty("user.dir") + "/src/main/resources/json/customers.json";

    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;
    private final Parser gson;
    private final FileUtil fileUtil;

    @Autowired
    public AppController(SupplierService supplierService,
                         PartService partService,
                         CarService carService,
                         CustomerService customerService,
                         SaleService saleService,
                         Parser gson,
                         FileUtil fileUtil) {
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;
        this.gson = gson;

        this.fileUtil = fileUtil;
    }

    @Override
    public void run(String... args) throws IOException {
        /*You can find all queries in Resources-> json -> export
        * If you don't want to scroll down all night :)*/

        System.out.println(this.importSuppliers());
        System.out.println(this.importParts());
        System.out.println(this.importCars());
        System.out.println(this.importCustomers());
        System.out.println(this.importSales());
        System.out.println("---------------Query 1 START---------------");
        System.out.println(this.orderCustomers());
        System.out.println("---------------     END     ---------------");
        System.out.println("---------------Query 2 START---------------");
        System.out.println(this.carsFromMakeToyota());
        System.out.println("---------------     END     ---------------");
        System.out.println("---------------Query 3 START---------------");
        System.out.println(this.localSuppliers());
        System.out.println("---------------     END     ---------------");
        System.out.println("---------------Query 4 START---------------");
        System.out.println(this.carsWithTheirListOfParts());
        System.out.println("---------------     END     ---------------");
        System.out.println("---------------Query 5 START---------------");
        System.out.println(this.totalSalesByCustomer());
        System.out.println("---------------     END     ---------------");
        System.out.println("---------------Query 6 START---------------");
        System.out.println(this.salesWithAppliedDiscount());
        System.out.println("---------------     END     ---------------");
    }

    private String salesWithAppliedDiscount() {
        return this.saleService.findAllSalesWithDiscount();
    }

    private String totalSalesByCustomer() {
        return this.saleService.findAllSaleHistoryByCustomer();
    }

    private String carsWithTheirListOfParts() throws IOException {
        return this.carService.findAllCarsAlongWithTheirParts();
    }

    private String localSuppliers() {

        return this.supplierService.findAllNonImporters();
    }


    private String carsFromMakeToyota() {
        return this.carService.findAllCarsFromMakeToyota("Toyota");
    }

    private String orderCustomers() {
        return this.customerService.orderCustomers();
    }

    private String importSales() {
        this.saleService.importSales();


        return "Imported sales";
    }

    private String importCars() throws IOException {
        String content = this.fileUtil.readFileContent(CARS_JSON_FILE_PATH);
        CarSeedDto[] carSeedDto = this.gson.exportFileContent(content, CarSeedDto[].class);

        this.carService.importCars(carSeedDto);
        return "Imported cars";
    }

    private String importCustomers() throws IOException {
        String content = this.fileUtil.readFileContent(CUSTOMERS_JSON_FILE_PATH);
        CustomerSeedDto[] customerSeedDtos = this.gson.exportFileContent(content, CustomerSeedDto[].class);

        this.customerService.importCustomers(customerSeedDtos);
        return "Imported Customers";
    }

    private String importSuppliers() throws IOException {
        String content = this.fileUtil.readFileContent(SUPPLIERS_JSON_FILE_PATH);
        SupplierSeedDto[] supplierSeedDtos = this.gson.exportFileContent(content, SupplierSeedDto[].class);

        this.supplierService.importSuppliers(supplierSeedDtos);
        return "Imported suppliers";
    }

    private String importParts() throws IOException {
        String content = this.fileUtil.readFileContent(PARTS_JSON_FILE_PATH);
        PartSeedDto[] partSeedDtos = this.gson.exportFileContent(content, PartSeedDto[].class);

        this.partService.importParts(partSeedDtos);
        return "Imported parts";
    }

}
