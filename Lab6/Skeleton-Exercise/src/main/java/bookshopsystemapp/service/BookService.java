package bookshopsystemapp.service;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface BookService {

    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();

    Set<String> getAllAuthorsWithBookBefore();

    List<String> getAllBookTitlesWithAgeRest();

    List<String> getGoldenBooks();

    List<String> getBookTitlesAndPricesForRange();

    List<String> getBooksNotInYear();

    List<String> getAllBooksBefore();

    List<String> getAllTitlesContaining();

    List<String> getAllBooksWithAuthorsLastNameStartingWith();

    String getAllBooksWithTitleLongerThan();

    List<String> getTotalCopiesByAuthor();

    List<String> getReducedBooks();

    String increaseBookCopies();
}
