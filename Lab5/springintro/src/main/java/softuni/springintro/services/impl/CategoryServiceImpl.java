package softuni.springintro.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springintro.entities.Category;
import softuni.springintro.repositories.CategoryRepositort;
import softuni.springintro.services.CategoryService;
import softuni.springintro.util.FileUtil;

import java.io.IOException;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final String CATEGORY_FILE_PATH =
            "/Users/macbookair/Downloads/springintro/src/main/resources/files/categories.txt";
    private final CategoryRepositort categoryRepository;
    private final FileUtil fileUtil;

    @Autowired
    public CategoryServiceImpl(CategoryRepositort categoryRepository, FileUtil fileUtil) {
        this.categoryRepository = categoryRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedCategory() throws IOException {
        if (this.categoryRepository.count() != 0) { return; }

        String[] categories = this.fileUtil.fileContent(CATEGORY_FILE_PATH);

        for (String s : categories) {
            Category category = new Category();
            category.setName(s);
            this.categoryRepository.saveAndFlush(category);
        }
    }
}
