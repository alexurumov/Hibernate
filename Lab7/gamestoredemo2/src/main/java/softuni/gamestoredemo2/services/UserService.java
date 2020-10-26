package softuni.gamestoredemo2.services;

import softuni.gamestoredemo2.domain.dtos.UserLoginDto;
import softuni.gamestoredemo2.domain.dtos.UserRegisterDto;

import java.util.List;

public interface UserService {
    String registerUser(UserRegisterDto userRegisterDto);

    String loginUser(UserLoginDto userLoginDto);

    String logoutUser();

    String getLoggedInUser();

    String addItem(String title);

    String removeItem(String title);

    String buyItem();

    String getOwnedBooks();

}
