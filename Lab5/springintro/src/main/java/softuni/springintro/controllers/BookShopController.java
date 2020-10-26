package softuni.springintro.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.springintro.services.AuthorService;
import softuni.springintro.services.BookService;
import softuni.springintro.services.CategoryService;

@Controller
public class BookShopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookShopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.authorService.seedAuthors();
        this.categoryService.seedCategory();
        this.bookService.seedBooks();

//        this.bookService.findAllTitles().forEach(System.out::println);
//        this.bookService.findAllAuthors().forEach(System.out::println);
//        this.authorService.findAllAuthorsOrdered().forEach(System.out::println);

        this.bookService.findAuthorBooksOrdered().forEach(System.out::println);
    }
}
