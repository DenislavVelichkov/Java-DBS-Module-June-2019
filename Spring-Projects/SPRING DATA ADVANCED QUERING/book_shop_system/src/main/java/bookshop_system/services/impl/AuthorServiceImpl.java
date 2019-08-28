package bookshop_system.services.impl;

import bookshop_system.models.Author;
import bookshop_system.repositories.AuthorRepository;
import bookshop_system.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Scanner;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private Scanner sc;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.sc = new Scanner(System.in);
    }

    @Override
    public List<Author> findAllAuthorsWithFirstNameLike() {
        return this.authorRepository.findAllByFirstNameEndsWith(sc.nextLine());
    }

    @Override
    public List<Author> findAllAuthorsAndBooksCount() {
        return this.authorRepository.findAll();
    }

    @Override
    public void seedAuthors(String[] fileData) {

        for (String line : fileData) {
            String[] params = line.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);
            if (this.authorRepository
                    .findAllByFirstNameAndLastName(params[0], params[1])
                    .isEmpty()) {

                this.authorRepository.saveAndFlush(author);
            }
        }


    }
}
