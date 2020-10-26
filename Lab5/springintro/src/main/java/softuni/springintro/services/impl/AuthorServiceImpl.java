package softuni.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springintro.entities.Author;
import softuni.springintro.repositories.AuthorRepository;
import softuni.springintro.services.AuthorService;
import softuni.springintro.util.FileUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String AUTHOR_PATH_FILE =
            "/Users/macbookair/Downloads/springintro/src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {

        if (this.authorRepository.count() != 0) {
            return;
        }

        String[] authors = this.fileUtil.fileContent(AUTHOR_PATH_FILE);

        for (String s : authors) {
            String[] params = s.split("\\s+");
            Author author = new Author();
            author.setFirstName(params[0]);
            author.setLastName(params[1]);
            this.authorRepository.saveAndFlush(author);
        }
    }

    @Override
    public List<String> findAllAuthorsOrdered() {
        return this.authorRepository.findAll()
                .stream()
                .sorted((a, b) -> Integer.compare(b.getBooks().size(), a.getBooks().size()))
                .map(a -> String.format("%s, %s, %d", a.getFirstName(), a.getLastName(), a.getBooks().size()))
                .collect(Collectors.toList());
    }


}
