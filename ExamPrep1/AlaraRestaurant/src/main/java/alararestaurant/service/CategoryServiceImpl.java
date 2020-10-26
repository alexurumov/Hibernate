package alararestaurant.service;

import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public String exportCategoriesByCountOfItems() {
        StringBuilder sb = new StringBuilder();

        this.categoryRepository.findAll()
                .stream()
                .sorted((c1, c2) -> {
                    if (c2.getItems().size() == c1.getItems().size()) {
                        return this.getItemsPricesSum(c2).compareTo(this.getItemsPricesSum(c1));
                    }

                    return Integer.compare(c2.getItems().size(), c1.getItems().size());
                })
                .forEach(c -> sb.append(c.toString()));

        return sb.toString();
    }

    private BigDecimal getItemsPricesSum(Category category) {
        BigDecimal sum = BigDecimal.ZERO;

        for (Item item : category.getItems()) {
            sum = sum.add(item.getPrice());
        }

        return sum;
    }
}
