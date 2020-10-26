package softuni.springintro2.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.springintro2.entities.Deleted;
import softuni.springintro2.entities.User;
import softuni.springintro2.repositories.UserRepository;
import softuni.springintro2.services.UserService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private Scanner in = new Scanner(System.in);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<String> usersByEmailProvider() {

        String emailProvider = this.in.nextLine();

        return this.userRepository.findAllByEmailContaining(emailProvider)
                .stream()
                .map(u -> String.format("%s %s", u.getUsername(), u.getEmail()))
                .collect(Collectors.toList());
    }

    @Override
    public String removeInactiveUsers() {

        LocalDateTime dateLimit = LocalDateTime.parse(this.in.nextLine());

        this.userRepository.findAllByLastTimeLoggedInBefore(dateLimit)
                .forEach(user -> user.setIsDeleted(Deleted.DELETED));

        List<User> forDeletion = new ArrayList<>(this.userRepository.findAllByIsDeleted(Deleted.DELETED));

        forDeletion.forEach(userRepository::delete);

        return String.format("%s %s been deleted",
                forDeletion.size() == 0 ? "No" : forDeletion.size(),
                forDeletion.size() == 1 ? "user has" : "users have");
    }
}
