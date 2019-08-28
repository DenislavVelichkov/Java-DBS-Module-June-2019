package bookshop_system.services;

import bookshop_system.models.Book;

import java.util.List;

public interface BookService {
    void seedBooks(String[] fileData);

    List<String> findAllTitlesByAgeRestriction(String ageRestriction);

    List<String> findAllGoldenEditionBooks();

    List<Book> findAllByPriceRange();

    List<String> findAllTitlesHadNotReleasedInGivenYear(int year);

    List<Book> findAllByReleaseDateBefore(String dateString);

    List<String> findAllTitlesContaining(String symbols);

    List<Book> findAllTitlesByAuthorLastName(String symbols);

    long findAllByTitleLength(int titleLength);

    List<Book> findReducedInfoAboutABook(String title);
}
