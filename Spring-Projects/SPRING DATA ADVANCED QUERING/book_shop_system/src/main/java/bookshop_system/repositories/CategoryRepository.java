package bookshop_system.repositories;

import bookshop_system.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByCategoryName(String categoryName);

    Category findById(int index);
}
