package com.cardealer.car_dealer.repository;

import com.cardealer.car_dealer.domain.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Car> findAllByTravelledDistance(Double distance);

    Optional<Car> findById(Integer id);

    @Query(value =
            "SELECT c FROM Car AS c " +
            "WHERE c.make = :make " +
            "ORDER BY c.model, c.travelledDistance DESC")
    List<Car> findAllByMakeOrderByModelTravelledDistanceDesc(String make);
}
