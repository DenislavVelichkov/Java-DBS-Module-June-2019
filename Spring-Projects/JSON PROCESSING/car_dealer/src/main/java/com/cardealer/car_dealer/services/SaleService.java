package com.cardealer.car_dealer.services;

public interface SaleService {

    void importSales();

    String findAllSaleHistoryByCustomer();

    String findAllSalesWithDiscount();
}
