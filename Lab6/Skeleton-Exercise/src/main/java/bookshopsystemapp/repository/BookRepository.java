package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal priceUpperBound, BigDecimal priceLowerBound);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate dateBefore, LocalDate dateAfter);

    List<Book> findAllByTitleContaining(String containing);

    @Query(name = "SELECT b FROM Book b JOIN b.authors a WHERE a.lastName LIKE CONCAT(:startString, '%')")
    List<Book> findAllByAuthorLastNameStartsWith(@Param(value = "startString") String startString);

    @Query("SELECT CONCAT(a.firstName, ' ', a.lastName), SUM(b.copies) FROM Book AS b JOIN b.author AS a GROUP BY CONCAT(a.firstName, ' ', a.lastName) ORDER BY SUM(b.copies) DESC ")
    List<Object[]> groupBookAllCopiesByAuthor();

    @Query("SELECT b.title, b.editionType, b.ageRestriction, b.price FROM Book AS b WHERE b.title LIKE :givenTitle")
    List<Object[]> getReducedBooks(@Param(value = "givenTitle") String givenTitle);

    @Transactional
    @Modifying
    @Query("UPDATE Book AS b SET b.copies = b.copies + :additionalCopies WHERE b.releaseDate > :date")
    void increaseBookCopies(@Param(value = "additionalCopies") int additionalCopies, @Param(value = "date") LocalDate date);

}
