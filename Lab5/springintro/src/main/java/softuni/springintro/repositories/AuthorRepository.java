package softuni.springintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintro.entities.Author;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    Author findAuthorByFirstNameAndLastName(String firstName, String lastName);

}
