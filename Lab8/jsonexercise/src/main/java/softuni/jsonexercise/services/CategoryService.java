package softuni.jsonexercise.services;

import softuni.jsonexercise.domain.dtos.CategoryByProductDto;
import softuni.jsonexercise.domain.dtos.CategorySeedDto;

import java.util.List;

public interface CategoryService {
    void seedCategories(CategorySeedDto[] categorySeedDtos);

    List<CategoryByProductDto> getCategoriesByProducts();

}
