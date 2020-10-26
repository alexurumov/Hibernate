package softuni.jsonexercise.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonexercise.domain.dtos.ProductInRangeDto;
import softuni.jsonexercise.domain.dtos.ProductSeedDto;
import softuni.jsonexercise.domain.entities.Category;
import softuni.jsonexercise.domain.entities.Product;
import softuni.jsonexercise.domain.entities.User;
import softuni.jsonexercise.repositories.CategoryRepository;
import softuni.jsonexercise.repositories.ProductRepository;
import softuni.jsonexercise.repositories.UserRepository;
import softuni.jsonexercise.services.ProductService;
import softuni.jsonexercise.util.ValidationUtil;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, ProductRepository productRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public void seedProducts(ProductSeedDto[] productSeedDtos) {
        for (ProductSeedDto dto : productSeedDtos) {
            dto.setSeller(this.getRandomSeller());
            dto.setBuyer(this.getRandomBuyer());
            dto.setCategories(this.getRandomCategories());

            if(!this.validationUtil.isValid(dto)) {
                System.out.println(this.validationUtil.violations(dto));

                continue;
            }

            Product product = this.modelMapper.map(dto, Product.class);

            this.productRepository.saveAndFlush(product);
        }
    }

    @Override
    public List<ProductInRangeDto> productsInRange(BigDecimal lowerBound, BigDecimal upperBound) {
        return this.productRepository.findByPriceBetweenAndBuyerIsNullOrderByPrice(lowerBound, upperBound)
                .stream()
                .map(product -> {
                    ProductInRangeDto dto = this.modelMapper.map(product, ProductInRangeDto.class);
                    dto.setSeller(product.getSeller().getFirstName() + " " + product.getSeller().getLastName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private User getRandomSeller() {
        Random random = new Random();

        int id = random.nextInt((int)this.userRepository.count() - 1) + 1;

        return this.userRepository.findById(id).orElse(null);
    }

    private User getRandomBuyer() {
        Random random = new Random();

        int id = random.nextInt((int)this.userRepository.count() - 1) + 1;

        if (id % 4 == 0) {
            return null;
        }

        return this.userRepository.findById(id).orElse(null);
    }

    private List<Category> getRandomCategories() {
        Random random = new Random();

        int id = random.nextInt((int)this.categoryRepository.count() - 1) + 1;

        int size = random.nextInt((int)this.categoryRepository.count() - 1) + 1;

        List<Category> categories = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            categories.add(this.categoryRepository.findById(id).orElse(null));
        }

        return categories;
    }

}
