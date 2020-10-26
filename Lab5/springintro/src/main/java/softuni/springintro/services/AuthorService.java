package softuni.springintro.services;

import softuni.springintro.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;

    List<String> findAllAuthorsOrdered();

}
