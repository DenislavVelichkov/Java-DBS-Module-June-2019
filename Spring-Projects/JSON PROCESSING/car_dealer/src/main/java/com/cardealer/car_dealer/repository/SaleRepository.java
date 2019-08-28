package com.cardealer.car_dealer.repository;

import com.cardealer.car_dealer.domain.models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {

    @Query(value =
            "SELECT s FROM Sale AS s WHERE s.customer IS NOT EMPTY")
    List<Sale> findAllCustomersWithSale();
}
