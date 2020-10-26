package softuni.jsonexercise.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonexercise.domain.dtos.CategoryByProductDto;
import softuni.jsonexercise.domain.dtos.CategorySeedDto;
import softuni.jsonexercise.domain.entities.Category;
import softuni.jsonexercise.domain.entities.Product;
import softuni.jsonexercise.repositories.CategoryRepository;
import softuni.jsonexercise.services.CategoryService;
import softuni.jsonexercise.util.ValidationUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedCategories(CategorySeedDto[] categorySeedDtos) {
        for (CategorySeedDto dto : categorySeedDtos) {
            if (!this.validationUtil.isValid(dto)) {
                System.out.println(this.validationUtil.violations(dto));

                continue;
            }

            Category category = modelMapper.map(dto, Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public List<CategoryByProductDto> getCategoriesByProducts() {

        List<Category> categories = this.categoryRepository.findCategoriesByProductsCount()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.getProducts().size(), c1.getProducts().size()))
                .collect(Collectors.toList());

        List<CategoryByProductDto> collect = categories.stream().map(c -> {
            CategoryByProductDto dto = new CategoryByProductDto();
            dto.setCategory(c.getName());
            dto.setProductsCount(c.getProducts().size());
            dto.setAveragePrice((this.getAverage(c.getProducts())));
            dto.setTotalRevenue(this.getSum(c.getProducts()));
            return dto;
        }).collect(Collectors.toList());

        return collect;
    }

    private BigDecimal getAverage(List<Product> products) {
        List<BigDecimal> prices = products.stream().map(Product::getPrice).collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal price : prices) {
            sum = sum.add(price);
        }

        return sum.divide(BigDecimal.valueOf(prices.size()), RoundingMode.HALF_UP);
    }

    private BigDecimal getSum(List<Product> products) {
        List<BigDecimal> prices = products.stream().map(Product::getPrice).collect(Collectors.toList());

        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal price : prices) {
            sum = sum.add(price);
        }

        return sum;
    }

}

