package softuni.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springintro.entities.*;
import softuni.springintro.repositories.AuthorRepository;
import softuni.springintro.repositories.BookRepository;
import softuni.springintro.repositories.CategoryRepositort;
import softuni.springintro.services.BookService;
import softuni.springintro.util.FileUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOK_FILE_PATH =
            "/Users/macbookair/Downloads/springintro/src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepositort categoryRepositort;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepositort categoryRepositort, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepositort = categoryRepositort;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) { return; }

        String[] books = this.fileUtil.fileContent(BOOK_FILE_PATH);

        for (String s : books) {
            String[] params = s.split("\\s+");
            Book book = new Book();
            book.setAuthor(randomAuthor());
            EditionType editionType = EditionType.values()[Integer.parseInt(params[0])];
            book.setEditionType(editionType);
            LocalDate releaseDate = LocalDate.parse(params[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);
            book.setCopies(Integer.parseInt(params[2]));
            book.setPrice(new BigDecimal(params[3]));
            book.setAgeRestriction(AgeRestriction.values()[Integer.parseInt(params[4])]);
            String[] titleParts = Arrays.stream(params).skip(5).toArray(String[]::new);
            String title = String.join(" ", titleParts);
            book.setTitle(title);
            book.setCategories(randomCategories());
            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> findAllTitles() {

        LocalDate dateAfter = LocalDate.parse("31/12/2000", DateTimeFormatter.ofPattern("d/M/yyyy"));

        return this.bookRepository.findAllByReleaseDateAfter(dateAfter)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllAuthors() {
        LocalDate dateBefore = LocalDate.parse("1/1/1990", DateTimeFormatter.ofPattern("d/M/yyyy"));

        return this.bookRepository.findAllByReleaseDateBefore(dateBefore)
                .stream()
                .map(b -> String.format("%s %s",
                        b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .collect(Collectors.toList());

    }

    @Override
    public List<String> findAuthorBooksOrdered() {

        String firstName = "George";
        String lastName = "Powell";

        Author author = this.authorRepository.findAuthorByFirstNameAndLastName(firstName, lastName);

        return this.bookRepository.findAllByAuthorOrderByReleaseDateDescTitleAsc(author)
                .stream()
                .map(b -> String.format("%s, %s, %d", b.getTitle(), b.getReleaseDate(), b.getCopies()))
                .collect(Collectors.toList());
    }

    private Author randomAuthor() {
        Random random = new Random();

        int index = random.nextInt((int) this.authorRepository.count()) + 1;

        return this.authorRepository.getOne(index);
    }

    private Category randomCategory() {
        Random random = new Random();

        int index = random.nextInt((int) this.categoryRepositort.count()) + 1;

        return this.categoryRepositort.getOne(index);
    }

    private Set<Category> randomCategories() {
        Set<Category> categories = new HashSet<>();

        Random random = new Random();
        int index = random.nextInt((int) this.categoryRepositort.count()) + 1;

        for (int i = 0; i < index; i++) {
            categories.add(randomCategory());
        }

        return categories;
    }
}
