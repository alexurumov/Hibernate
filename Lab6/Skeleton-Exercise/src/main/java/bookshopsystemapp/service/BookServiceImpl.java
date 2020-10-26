package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final static String BOOKS_FILE_PATH = "/Users/macbookair/Downloads/Hibernate/Lab6/Skeleton-Exercise/src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, CategoryRepository categoryRepository, FileUtil fileUtil) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedBooks() throws IOException {
        if (this.bookRepository.count() != 0) {
            return;
        }

        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);
        for (String line : booksFileContent) {
            String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i < lineParams.length; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();
            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);
        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books = this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));

        return books.stream().map(Book::getTitle).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));

        return books.stream().map(b -> String.format("%s %s", b.getAuthor().getFirstName(), b.getAuthor().getLastName())).collect(Collectors.toSet());
    }

    @Override
    public List<String> getAllBookTitlesWithAgeRest() {
        Scanner in = new Scanner(System.in);
        String ageRest = in.nextLine();
        AgeRestriction ageRestriction = AgeRestriction.valueOf(ageRest.toUpperCase());

        return this.bookRepository.findAllByAgeRestriction(ageRestriction)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getGoldenBooks() {
        EditionType editionType = EditionType.GOLD;
        int copies = 5000;

        return this.bookRepository.findAllByEditionTypeAndCopiesIsLessThan(editionType, copies)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBookTitlesAndPricesForRange() {
        BigDecimal upperBound = BigDecimal.valueOf(5.00);
        BigDecimal lowerBound = BigDecimal.valueOf(40.00);

        return this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(upperBound, lowerBound)
                .stream()
                .map(b -> String.format("%s %.2f", b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBooksNotInYear() {
        Scanner in = new Scanner(System.in);
        int year = Integer.parseInt(in.nextLine());
        LocalDate dateBefore = LocalDate.of(year, 1, 1);
        LocalDate dateAfter = LocalDate.of(year, 12, 31);

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(dateBefore, dateAfter)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksBefore() {
        Scanner in = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate dateBefore = LocalDate.parse(in.nextLine(), formatter);

        return this.bookRepository.findAllByReleaseDateBefore(dateBefore)
                .stream()
                .map(b -> String.format("%s %s %2f", b.getTitle(), b.getEditionType(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTitlesContaining() {
        Scanner in = new Scanner(System.in);
        String containing = in.nextLine().toLowerCase();

        return this.bookRepository.findAllByTitleContaining(containing)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllBooksWithAuthorsLastNameStartingWith() {
        Scanner in = new Scanner(System.in);
        String startString = in.nextLine().toLowerCase();

        return this.bookRepository.findAllByAuthorLastNameStartsWith(startString)
                .stream()
                .map(Book::getTitle)
                .collect(Collectors.toList());
    }

    @Override
    public String getAllBooksWithTitleLongerThan() {
        Scanner in = new Scanner(System.in);
        int length = Integer.parseInt(in.nextLine());

        int booksLonger = (int) this.bookRepository.findAll()
                .stream()
                .filter(b -> b.getTitle().length() > length).count();

        String result = String.format("There %s %d %s with longer title than %d %s",
                booksLonger == 1 ? "is" : "are",
                booksLonger,
                booksLonger == 1 ? "book" : "books",
                length,
                length == 1 ? "symbol" : "symbols");

        return result;
    }

    @Override
    public List<String> getTotalCopiesByAuthor() {

        List<String> endResult = new ArrayList<>();

        for (Object[] result : this.bookRepository.groupBookAllCopiesByAuthor()) {
            String format = String.format("%s - %s",
                    result[0],
                    result[1]);

            endResult.add(format);
        }

        return endResult;
    }

    @Override
    public List<String> getReducedBooks() {
        Scanner in = new Scanner(System.in);
        String givenTitle = in.nextLine();

        List<String> endResult = new ArrayList<>();

        for (Object[] result : this.bookRepository.getReducedBooks(givenTitle)) {
            String format = String.format("%s %s %s %s",
                    result[0],
                    result[1],
                    result[2],
                    result[3]);
            endResult.add(format);
        }

        return endResult;
    }

    @Override
    public String increaseBookCopies() {
        Scanner in = new Scanner(System.in);

        String dateInput = in.nextLine();

        LocalDate date = LocalDate.parse(dateInput);

        int additionalCopies = Integer.parseInt(in.nextLine());

        int updated = this.bookRepository.findAllByReleaseDateAfter(date).size();

        this.bookRepository.increaseBookCopies(additionalCopies, date);

        return String.format("%d books are released after %s, so total of %d book copies were added",
                updated, dateInput, updated * additionalCopies);
    }

    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() - 1)) + 1;

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories() {
        Set<Category> categories = new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }

        return categories;
    }

    private Category getRandomCategory() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() - 1)) + 1;

        return this.categoryRepository.findById(randomId).orElse(null);
    }
}
