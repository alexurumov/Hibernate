package softuni.jsonexercise.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.jsonexercise.domain.dtos.*;
import softuni.jsonexercise.domain.entities.Product;
import softuni.jsonexercise.domain.entities.User;
import softuni.jsonexercise.repositories.UserRepository;
import softuni.jsonexercise.services.UserService;
import softuni.jsonexercise.util.ValidationUtil;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(ValidationUtil validationUtil, ModelMapper modelMapper, UserRepository userRepository) {
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void seedUsers(UserSeedDto[] userSeedDtos) {
        for (UserSeedDto dto : userSeedDtos) {
            if (!this.validationUtil.isValid(dto)) {
                System.out.println(this.validationUtil.violations(dto));

                continue;
            }

            User user = this.modelMapper.map(dto, User.class);
            this.userRepository.saveAndFlush(user);
        }

    }

    @Override
    public List<UserSoldProductsDto> getAllUsersSoldProducst() {

        return this.userRepository.getAllUsersSoldProducts()
                .stream()
                .map(user -> this.modelMapper.map(user, UserSoldProductsDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsersAndProductsDto getAllUsersWithAtLeastOneProductSold() {

        List<User> users = this.userRepository.getAllUsersSoldProducts();

        UsersAndProductsDto mainDto = new UsersAndProductsDto();
        mainDto.setUsersCount(users.size());

        List<UserWithOneProductDto> usersWithProducts = new ArrayList<>();

        for (User user : users) {
            UserWithOneProductDto usersDto = new UserWithOneProductDto();
            usersDto.setFirstName(user.getFirstName());
            usersDto.setLastName(user.getLastName());
            usersDto.setAge(user.getAge());

                ProductsSold2Dto productsSoldDto = new ProductsSold2Dto();
                productsSoldDto.setCount(user.getProductsSold().size());

                    List<ProductWithPriceDto> products = new ArrayList<>();
                    for (Product product : user.getProductsSold()) {
                        ProductWithPriceDto productDto = new ProductWithPriceDto();
                        productDto.setName(product.getName());
                        productDto.setPrice(product.getPrice());
                    products.add(productDto);
                    }
                productsSoldDto.setProducts(products);

            usersDto.setProductsSold(productsSoldDto);
            usersWithProducts.add(usersDto);
        }

        mainDto.setUsers(usersWithProducts);

        return mainDto;
    }
}
