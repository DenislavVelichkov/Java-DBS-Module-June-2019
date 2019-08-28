package bookshop_system.controller;

import bookshop_system.models.Book;
import bookshop_system.services.impl.AuthorServiceImpl;
import bookshop_system.services.impl.BookServiceImpl;
import bookshop_system.services.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class AppController implements CommandLineRunner {
    private final static Path BOOKS_PATH = Path.of("books.txt");
    private final static Path AUTHORS_PATH = Path.of("authors.txt");
    private final static Path CATEGORIES_PATH = Path.of("categories.txt");
    private Scanner sc;
    private BookServiceImpl bookService;
    private AuthorServiceImpl authorService;
    private CategoryServiceImpl categoryService;

    @Autowired
    public AppController(BookServiceImpl bookService,
                         AuthorServiceImpl authorService,
                         CategoryServiceImpl categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.sc = new Scanner(System.in);
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors(this.seedDatabase(AUTHORS_PATH));
        this.categoryService.seedCategories(this.seedDatabase(CATEGORIES_PATH));
        this.bookService.seedBooks(this.seedDatabase(BOOKS_PATH));

//        1.	Books Titles by Age Restriction
        System.out.println("---------------PROBLEM 1 START-------------------");
        System.out.println("Enter age restriction criteria: ");
        this.bookService
            .findAllTitlesByAgeRestriction(sc.nextLine())
            .forEach(System.out::println);
        System.out.println("---------------END OF PROBLEM 1-------------------");

//        2.	Golden Books
        System.out.println("---------------PROBLEM 2 START-------------------");
        this.bookService
            .findAllGoldenEditionBooks()
            .forEach(System.out::println);
        System.out.println("---------------END OF PROBLEM 2-------------------");

//        3.	Books by Price
        System.out.println("---------------PROBLEM 3 START-------------------");
        this.bookService.findAllByPriceRange()
            .forEach(book -> System.out.println(
                    String.format("%s $%s",
                    book.getTitle(), book.getPrice())));
        System.out.println("---------------END OF PROBLEM 3-------------------");

//        4.	Not Released Books
        System.out.println("---------------PROBLEM 4 START-------------------");
        System.out.println("Exclude a year from database seed: ");
        this.bookService
            .findAllTitlesHadNotReleasedInGivenYear(Integer.parseInt(sc.nextLine()))
            .forEach(System.out::println);
        System.out.println("---------------END OF PROBLEM 4-------------------");

//        5.	Books Released Before Date
        System.out.println("---------------PROBLEM 5 START-------------------");
        System.out.println("Enter a release date in format(dd-MM-yyyy): ");
        this.bookService.findAllByReleaseDateBefore(sc.nextLine())
            .forEach(book -> System.out.println(
                String.format("%s %s %s",
                book.getTitle(), book.getEditionType(), book.getPrice())));
        System.out.println("---------------END OF PROBLEM 5-------------------");

//        6.	Authors Search
        System.out.println("---------------PROBLEM 6 START-------------------");
        System.out.println("Prints the names of those authors, whose first name ends with a given string.");
        System.out.println("Enter some letters: ");
        this.authorService
            .findAllAuthorsWithFirstNameLike()
            .forEach(author -> System.out.println(
                String.format("%s %s",
                    author.getFirstName(), author.getLastName())));

        System.out.println("---------------END OF PROBLEM 6------------------");

//        7.	Books Search
        System.out.println("---------------PROBLEM 7 START-------------------");
        System.out.println("Prints the titles of books," +
                               " which contain a given string (regardless of the casing).");
        System.out.println("Enter a string: ");
        this.bookService
            .findAllTitlesContaining(sc.nextLine())
            .forEach(System.out::println);
        System.out.println("---------------END OF PROBLEM 7------------------");

//        8.	Book Titles Search
        System.out.println("---------------PROBLEM 8 START-------------------");
        System.out.println("Prints the titles of books, which are written by authors," +
                               " whose last name starts with a given string");
        System.out.println("Enter a string (example: sK): ");
        this.bookService
            .findAllTitlesByAuthorLastName(sc.nextLine())
            .forEach(book -> System.out.println(
                String.format("%s (%s %s)",
                    book.getTitle(),
                    book.getAuthor().getFirstName(),
                    book.getAuthor().getLastName())));
        System.out.println("---------------END OF PROBLEM 8------------------");

//        9.	Count Books
        System.out.println("---------------PROBLEM 9 START-------------------");
        System.out.println("Enter desired title length {number}: ");
        int titleLengthCriteria = Integer.parseInt(sc.nextLine());
        int titlesCount = (int) this.bookService
                                    .findAllByTitleLength(titleLengthCriteria);
        System.out.println(String.format(
            "There are %d books with longer title than %d symbols",
            titlesCount, titleLengthCriteria));
        System.out.println("---------------END OF PROBLEM 9------------------");

//        10.	 Total Book Copies
        System.out.println("---------------PROBLEM 10 START-------------------");
        this.authorService.findAllAuthorsAndBooksCount()
            .stream()
            .sorted((b1, b2) -> {
                int copiesB1 = (int) b1.getBooks().stream().mapToLong(Book::getCopies).sum();
                int copiesB2 = (int) b2.getBooks().stream().mapToLong(Book::getCopies).sum();
                return Integer.compare(copiesB2, copiesB1);
            })
            .forEach(author -> System.out.println(String.format("%s %s - %d",
                author.getFirstName(),
                author.getLastName(),
                (int) author.getBooks().stream().mapToLong(Book::getCopies).sum())));
        System.out.println("---------------END OF PROBLEM 10------------------");

//        11.	Reduced Book
        System.out.println("---------------PROBLEM 11 START-------------------");
        System.out.println("Enter title: ");
        this.bookService.findReducedInfoAboutABook(sc.nextLine())
            .forEach(book -> System.out.println(
                String.format("%s %s %s %s",
                book.getTitle(), book.getEditionType(), book.getAgeRestriction(), book.getPrice())));
        System.out.println("---------------END OF PROBLEM 11------------------");
    }

    private String[] seedDatabase(Path path) throws IOException {
        File file = new File(path.toUri());
        BufferedReader bf = new BufferedReader(new FileReader(file));

        List<String> fileContent = new ArrayList<>();

        String line;
        while ((line = bf.readLine()) != null) {
            fileContent.add(line);
        }

        return fileContent.stream()
                   .filter(l -> !l.equals("")).toArray(String[]::new);
    }
}
