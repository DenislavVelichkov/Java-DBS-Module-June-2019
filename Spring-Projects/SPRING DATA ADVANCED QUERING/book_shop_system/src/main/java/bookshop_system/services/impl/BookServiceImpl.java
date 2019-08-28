package bookshop_system.services.impl;

import bookshop_system.models.*;
import bookshop_system.repositories.AuthorRepository;
import bookshop_system.repositories.BookRepository;
import bookshop_system.repositories.CategoryRepository;
import bookshop_system.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private static final int GOLDEN_EDITION_COPIES_COUNT_CRITERIA = 5000;
    private static final BigDecimal PRICE_LOW_RANGE= BigDecimal.valueOf(5);
    private static final BigDecimal PRICE_HIGH_RANGE= BigDecimal.valueOf(40);
    private final BookRepository bookRepository;
    private  AuthorRepository authorRepository;
    private  CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks(String[] fileData) {
        for (String line : fileData) {
/*SimpleDateFormat formatter = new SimpleDateFormat("d/M/yyyy");
Date releaseDate = formatter.parse(data[1]);
AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(data[4])];*/
            Book book = new Book();
            String[] params = line.split("\\s+");
            book.setEditionType(this.getOrdinalOfEditType(Integer.parseInt(params[0])));
            book.setReleaseDate(this.correctDate(params[1]));
            book.setCopies(Integer.parseInt(params[2]));
            book.setPrice(new BigDecimal(params[3]));
            book.setAgeRestriction(this.getOrdinalOfAgeRest(Integer.parseInt(params[4])));

            StringBuilder titleBuilder = new StringBuilder();

            for (int i = 5; i < params.length; i++) {
                titleBuilder.append(params[i]).append(" ");
            }

            titleBuilder.deleteCharAt(titleBuilder.lastIndexOf(" "));
            String title = titleBuilder.toString();

            book.setTitle(title);
            book.setAuthor(this.getRandomAuthor());
            book.setCategories(this.getRANDomCategories());

            if (this.bookRepository.findAllByTitleContaining(title).isEmpty()) {
                this.bookRepository.saveAndFlush(book);
            }


        }
    }

    @Override
    public List<String> findAllTitlesByAgeRestriction(String ageRestriction) {
        return this.bookRepository
                   .findAllByAgeRestriction(AgeRestriction.valueOf(ageRestriction.toUpperCase()))
                   .stream()
                   .map(Book::getTitle)
                   .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllGoldenEditionBooks() {
        return this.bookRepository
                   .findAllByEditionTypeAndCopiesLessThan(EditionType.GOLD, GOLDEN_EDITION_COPIES_COUNT_CRITERIA)
                   .stream()
                   .map(Book::getTitle)
                   .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByPriceRange() {
        return
            this.bookRepository
                .findAllByPriceLessThanAndPriceGreaterThan(PRICE_LOW_RANGE, PRICE_HIGH_RANGE);

    }

    @Override
    public List<String> findAllTitlesHadNotReleasedInGivenYear(int year) {
        LocalDate rangeFrom = LocalDate.of(year, 1, 1);
        String endOfTheGivenYear = "" + rangeFrom.getYear() + "-12-31";
        LocalDate rangeTo = LocalDate.parse(endOfTheGivenYear);

        return this.bookRepository
                   .findAllByReleaseDateNotIn(rangeFrom, rangeTo)
                   .stream()
                   .map(Book::getTitle)
                   .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(String dateString) {
        LocalDate date = correctDate(dateString);
        return this.bookRepository.findAllByReleaseDateBefore(date);
    }

    @Override
    public List<String> findAllTitlesContaining(String symbols) {
        return this.bookRepository.findAllByTitleContaining(symbols)
            .stream()
            .map(Book::getTitle)
            .collect(Collectors.toList());
    }

    @Override
    public List<Book> findAllTitlesByAuthorLastName(String symbols) {
        return this.bookRepository.findAllByAuthorLastName(symbols);
    }

    @Override
    public long findAllByTitleLength(int titleLength) {
        return this.bookRepository
                   .findAllByTitleLength(titleLength)
                   .stream()
                   .map(Book::getTitle)
                   .count();
    }

    @Override
    public List<Book> findReducedInfoAboutABook(String title) {
        return this.bookRepository.findAllByTitle(title);
    }


    private LocalDate correctDate(String date) {
        String[] params;

        if (date.contains("/")) {
            params = date.split("/");
        } else {
            params = date.split("-");
        }

        return LocalDate.of(Integer.parseInt(params[2]),
            Integer.parseInt(params[1]),
            Integer.parseInt(params[0]));
    }

    private AgeRestriction getOrdinalOfAgeRest(int valueFromFile) {
        AgeRestriction resultValue = null;

        for (AgeRestriction value : AgeRestriction.values()) {
            if (value.ordinal() == valueFromFile) {
                resultValue = value;
                break;
            }
        }

        return resultValue;
    }

    private EditionType getOrdinalOfEditType(int valueFromFile) {
        EditionType resultValue = null;

        for (EditionType value : EditionType.values()) {
            if (value.ordinal() == valueFromFile) {
                resultValue = value;
                break;
            }
        }

        return resultValue;
    }

    private Category getRandomCategory() {
        Random random = new Random();
        int index = 0;

        while (index == 0) {
            index = random.nextInt((int) this.categoryRepository.count() + 1);
        }

        return this.categoryRepository.findById(index);
    }

    private Set<Category> getRANDomCategories() {
        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepository.count() + 1);

        Set<Category> categories = new LinkedHashSet<>();
        for (int i = 0; i < index; i++) {
            categories.add(this.getRandomCategory());
        }
        return categories;
    }

    private Author getRandomAuthor() {
        Random random = new Random();
        int index = 0;

        while (index == 0) {
            index = random.nextInt((int) this.authorRepository.count() + 1);
        }

        return this.authorRepository.findById(index);
    }
}
