package softuni.springintro2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.springintro2.entities.User;
import softuni.springintro2.repositories.UserRepository;
import softuni.springintro2.services.impl.UserServiceImpl;

import java.time.LocalDateTime;

@Controller
public class UserSystemController implements CommandLineRunner {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;

    @Autowired
    public UserSystemController(UserRepository userRepository, UserServiceImpl userService, UserRepository userRepository1) {
        this.userService = userService;
        this.userRepository = userRepository1;
    }

    @Override
    public void run(String... args) throws Exception {
//        this.userService.usersByEmailProvider().forEach(System.out::println);

        User user1 = new User();
        user1.setUsername("Sasho123");
        user1.setPassword("Sasho123@");
        user1.setEmail("sasho@softuni.bg");
        user1.setLastTimeLoggedIn(LocalDateTime.parse("2019-07-08T00:00:01"));
        user1.setAge(26);

        this.userRepository.saveAndFlush(user1);

        User user2 = new User();
        user2.setUsername("Sasho123");
        user2.setPassword("Sasho123@");
        user2.setEmail("sasho@softuni.bg");
        user2.setLastTimeLoggedIn(LocalDateTime.parse("2019-07-07T00:00:01"));

        user2.setAge(26);

        this.userRepository.saveAndFlush(user2);

        User user3 = new User();
        user3.setUsername("Sasho123");
        user3.setPassword("Sasho123@");
        user3.setEmail("sasho@softuni.bg");
        user3.setLastTimeLoggedIn(LocalDateTime.parse("2019-07-10T00:00:01"));

        user3.setAge(26);

        this.userRepository.saveAndFlush(user3);

        System.out.println(this.userService.removeInactiveUsers());
    }
}
