package softuni.xmlparsingdemo.services;

import softuni.xmlparsingdemo.domain.dtos.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers();

}
