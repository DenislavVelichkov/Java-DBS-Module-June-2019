package com.cardealer.car_dealer.repository;

import com.cardealer.car_dealer.domain.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findById(Integer id);

    Optional<Customer> findAllByName(String name);

    List<Customer> findAll();
}
