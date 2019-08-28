package bookshop_system.services;

import bookshop_system.models.Author;

import java.util.List;

public interface AuthorService {
    void seedAuthors(String[] fileData);

    List<Author> findAllAuthorsWithFirstNameLike();

    List<Author> findAllAuthorsAndBooksCount();
}
