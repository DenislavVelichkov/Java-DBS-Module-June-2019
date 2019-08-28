package bookshop_system.repositories;

import bookshop_system.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    List<Author> findAllByFirstNameAndLastName(String firstName, String lastName);

    Author findById(int index);

    List<Author> findAllByFirstNameEndsWith(String letters);

    @Query(value = "SELECT a FROM Author a INNER JOIN a.books GROUP BY a.id")
    List<Author> findAll();
}
