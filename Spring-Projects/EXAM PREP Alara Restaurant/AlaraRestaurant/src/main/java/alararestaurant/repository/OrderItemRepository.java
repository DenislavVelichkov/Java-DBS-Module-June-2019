package alararestaurant.repository;

import alararestaurant.models.entities.Order;
import alararestaurant.models.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    Optional<OrderItem> findAllByItem_Name(String itemName);
}
