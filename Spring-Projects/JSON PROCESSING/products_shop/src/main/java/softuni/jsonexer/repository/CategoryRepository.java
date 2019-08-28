package softuni.jsonexer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonexer.domain.models.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String categoryName);
    
    List<Category> findAllByProductsIsNotNull();

    @Query(value = "SELECT c FROM Category c " +
                   "JOIN c.products p " +
                   "GROUP BY c.name ")
    List<Category> findAllByCategories();
}
