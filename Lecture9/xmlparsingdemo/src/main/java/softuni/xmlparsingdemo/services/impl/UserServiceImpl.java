package softuni.xmlparsingdemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.xmlparsingdemo.domain.dtos.UserDto;
import softuni.xmlparsingdemo.repositories.UserRepository;
import softuni.xmlparsingdemo.services.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(u -> mapper.map(u, UserDto.class)).collect(Collectors.toList());
    }
}
