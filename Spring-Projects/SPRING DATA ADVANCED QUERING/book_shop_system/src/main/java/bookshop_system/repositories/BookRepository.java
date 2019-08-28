package bookshop_system.repositories;

import bookshop_system.models.AgeRestriction;
import bookshop_system.models.Book;
import bookshop_system.models.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findAll();

    List<Book> findAllByTitleContaining(String title);

    List<Book> findAllByReleaseDateAfter(LocalDate localDate);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copiesCount);

    @Query(value = "SELECT b FROM Book b WHERE b.price NOT BETWEEN :param1 AND :param2")
    List<Book> findAllByPriceLessThanAndPriceGreaterThan(BigDecimal param1, BigDecimal param2);

    @Query(value = "SELECT b FROM Book b WHERE b.releaseDate NOT BETWEEN :dateTime AND :dateTimeEnd")
    List<Book> findAllByReleaseDateNotIn(LocalDate dateTime, LocalDate dateTimeEnd);

    List<Book> findAllByReleaseDateBefore(LocalDate localDate);

    @Query("SELECT b FROM Book b INNER JOIN b.author a WHERE a.lastName like :letters%")
    List<Book> findAllByAuthorLastName(String letters);

    @Query(value = "SELECT b FROM Book b WHERE LENGTH(b.title) > :titleLength")
    List<Book> findAllByTitleLength(Integer titleLength);

    List<Book> findAllByTitle(String title);
}
