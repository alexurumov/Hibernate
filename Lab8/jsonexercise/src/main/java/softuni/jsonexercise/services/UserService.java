package softuni.jsonexercise.services;

import softuni.jsonexercise.domain.dtos.UserSeedDto;
import softuni.jsonexercise.domain.dtos.UserSoldProductsDto;
import softuni.jsonexercise.domain.dtos.UserWithOneProductDto;
import softuni.jsonexercise.domain.dtos.UsersAndProductsDto;

import java.util.List;

public interface UserService {

    void seedUsers(UserSeedDto[] userSeedDtos);

    List<UserSoldProductsDto> getAllUsersSoldProducst();


    UsersAndProductsDto getAllUsersWithAtLeastOneProductSold();
}
