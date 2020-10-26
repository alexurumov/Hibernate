package bookshopsystemapp.controller;

import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Set;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
        public void run(String... strings) throws Exception {
//        this.authorService.seedAuthors();
//        this.categoryService.seedCategories();
//        this.bookService.seedBooks();

//        this.printBookTitles();
//        this.printGoldenBookTitles();
//        this.printBookTitlesAndPricesInRange();
//        this.printBookTitlesNotInYear();
//        this.printBooksReleasedBefore();
//        this.printAuthorsSearch();
//        this.printBookTitlesContaining();
//        this.printBookTitlesWithAuthorsNameStartingWith();
//        this.printNumberOfBooksWithLongerTitle();
//        this.printTotalCopiesByAuthor();
//        this.printReducedBooks();
        this.increaseBookCopies();

    }

    private void increaseBookCopies() {
        System.out.println(this.bookService.increaseBookCopies());
    }

    private void printReducedBooks() {
        this.bookService.getReducedBooks().forEach(System.out::println);
    }

    private void printTotalCopiesByAuthor() {
        this.bookService.getTotalCopiesByAuthor().forEach(System.out::println);
    }

    private void printNumberOfBooksWithLongerTitle() {
        System.out.println(this.bookService.getAllBooksWithTitleLongerThan());
    }

    private void printBookTitlesWithAuthorsNameStartingWith() {
        this.bookService.getAllBooksWithAuthorsLastNameStartingWith().forEach(System.out::println);
    }

    private void printBookTitlesContaining() {
        this.bookService.getAllTitlesContaining().forEach(System.out::println);
    }

    private void printAuthorsSearch() {
        this.authorService.authorsSearch().forEach(System.out::println);
    }

    private void printBooksReleasedBefore() {
        this.bookService.getAllBooksBefore().forEach(System.out::println);
    }

    private void printBookTitlesNotInYear() {
        this.bookService.getBooksNotInYear().forEach(System.out::println);
    }

    private void printBookTitlesAndPricesInRange() {
        this.bookService.getBookTitlesAndPricesForRange().forEach(System.out::println);
    }

    private void printGoldenBookTitles() {
        this.bookService.getGoldenBooks().forEach(System.out::println);
    }

    private void printBookTitles() {
        List<String> titles = this.bookService.getAllBookTitlesWithAgeRest();

        titles.forEach(System.out::println);
    }
}
