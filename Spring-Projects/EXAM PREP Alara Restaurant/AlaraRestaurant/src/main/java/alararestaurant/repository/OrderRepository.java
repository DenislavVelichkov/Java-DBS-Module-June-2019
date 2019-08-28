package alararestaurant.repository;

import alararestaurant.models.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Optional<Order> findByEmployeeName(String employeeName);

    List<Order> findAllByEmployeePosition_Id(Integer positionId);
}
