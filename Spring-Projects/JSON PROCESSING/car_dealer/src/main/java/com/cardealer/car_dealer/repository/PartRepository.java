package com.cardealer.car_dealer.repository;

import com.cardealer.car_dealer.domain.models.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {

    Optional<Part> findAllByName(String name);
}
