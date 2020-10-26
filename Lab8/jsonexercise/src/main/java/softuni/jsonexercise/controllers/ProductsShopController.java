package softuni.jsonexercise.controllers;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.jsonexercise.domain.dtos.*;
import softuni.jsonexercise.services.CategoryService;
import softuni.jsonexercise.services.ProductService;
import softuni.jsonexercise.services.UserService;
import softuni.jsonexercise.util.FileUtil;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.util.List;

@Controller
public class ProductsShopController implements CommandLineRunner {

    private static final String USERS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab8/jsonexercise/src/main/resources/files/users.json";
    private static final String CATEGORIES_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab8/jsonexercise/src/main/resources/files/categories.json";
    private static final String PRODUCTS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab8/jsonexercise/src/main/resources/files/products.json";

    private final Gson gson;
    private final FileUtil fileUtil;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductsShopController(Gson gson, FileUtil fileUtil, UserService userService, CategoryService categoryService, ProductService productService) {
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

//        seedUsers();

//        seedCategories();

//        seedProducts();

//        exportProductsInRange();

//        exportUsersSoldProducts();

//        exportCategoriesByProducts();

        exportUsersWithAtLeastOneProductSold();
    }

    private void exportUsersWithAtLeastOneProductSold() {
        String usersJson = this.gson.toJson(this.userService.getAllUsersWithAtLeastOneProductSold());

        System.out.println(usersJson);
    }

    private void exportCategoriesByProducts() {
        List<CategoryByProductDto> categories = this.categoryService.getCategoriesByProducts();

        String categoriesJson = this.gson.toJson(categories);

        System.out.println(categoriesJson);

    }

    private void exportUsersSoldProducts() {

        List<UserSoldProductsDto> users = this.userService.getAllUsersSoldProducst();

        String usersJson = this.gson.toJson(users);

        System.out.println(usersJson);
    }

    private void exportProductsInRange() {
        List<ProductInRangeDto> products = this.productService.productsInRange(BigDecimal.valueOf(500), BigDecimal.valueOf(1000));

        String productsJson = this.gson.toJson(products);

        System.out.println(productsJson);
    }

    private void seedUsers() throws IOException {
        String content = this.fileUtil.fileContent(USERS_FILE_PATH);

        UserSeedDto[] userSeedDtos = this.gson.fromJson(content, UserSeedDto[].class);

        this.userService.seedUsers(userSeedDtos);
    }

    private void seedCategories() throws IOException {
        String content = this.fileUtil.fileContent(CATEGORIES_FILE_PATH);

        CategorySeedDto[] categorySeedDtos = this.gson.fromJson(content, CategorySeedDto[].class);

        this.categoryService.seedCategories(categorySeedDtos);
    }

    private void seedProducts() throws IOException {
        String content = this.fileUtil.fileContent(PRODUCTS_FILE_PATH);

        ProductSeedDto[] productSeedDtos = this.gson.fromJson(content, ProductSeedDto[].class);

        this.productService.seedProducts(productSeedDtos);
    }
}
