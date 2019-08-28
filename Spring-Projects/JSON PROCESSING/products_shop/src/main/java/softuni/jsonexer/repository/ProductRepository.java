package softuni.jsonexer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonexer.domain.models.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findByName(String productName);
    
    List<Product> findAllByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal more, BigDecimal less);

    @Query(value = "SELECT p FROM Product p " +
                   "WHERE p.seller IS NOT NULL AND p.buyer IS NOT NULL " +
                   "ORDER BY p.buyer.lastName, p.buyer.firstName")
    List<Product> findAllBySellerIsNotNullAndBuyerIsNotNull();

}
