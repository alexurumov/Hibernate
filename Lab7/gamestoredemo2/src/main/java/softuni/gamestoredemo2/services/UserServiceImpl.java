package softuni.gamestoredemo2.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestoredemo2.domain.dtos.UserLoginDto;
import softuni.gamestoredemo2.domain.dtos.UserRegisterDto;
import softuni.gamestoredemo2.domain.entities.Game;
import softuni.gamestoredemo2.domain.entities.Role;
import softuni.gamestoredemo2.domain.entities.User;
import softuni.gamestoredemo2.repositories.GameRepository;
import softuni.gamestoredemo2.repositories.UserRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private ModelMapper mapper;
    private String loggedInUser;
    private Set<Game> shoppingCart;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, GameRepository gameRepository) {
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.mapper = new ModelMapper();
        this.loggedInUser = "";
        this.shoppingCart = new HashSet<>();
    }

    @Override
    public String registerUser(UserRegisterDto userRegisterDto) {
        StringBuilder sb = new StringBuilder();

        User user = this.mapper.map(userRegisterDto, User.class);

        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violationSet = validator.validate(user);

        boolean present = this.userRepository.findByEmail(user.getEmail()).isPresent();
        if (present) {
            sb.append("User already exists");
            return sb.toString();
        }

        if (violationSet.size() > 0) {
            for (ConstraintViolation<User> violation : violationSet) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        } else {
            if (this.userRepository.count() == 0) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            sb.append(String.format("%s was registered", user.getFullName()));
            this.userRepository.saveAndFlush(user);
        }

        return sb.toString();
    }

    @Override
    public String loginUser(UserLoginDto userLoginDto) {
        StringBuilder sb = new StringBuilder();

        if (!this.loggedInUser.isEmpty()) {
            return sb.append("User already logged in").toString();
        }

        User user = this.userRepository.findByEmail(userLoginDto.getEmail()).orElse(null);

        if (user == null) {
            return sb.append("No such user").toString();
        } else {
            if (!user.getPassword().equals(userLoginDto.getPassword())) {
                return sb.append("Incorrect password").toString();
            }

            sb.append(String.format("Successfully logged in %s", user.getFullName()));
            this.loggedInUser = userLoginDto.getEmail();
        }

        return sb.toString();
    }

    @Override
    public String logoutUser() {
        StringBuilder sb = new StringBuilder();

        if (this.loggedInUser.isEmpty()) {
            return sb.append("Cannot log out. No user was logged in.").toString();
        } else {
            User user = this.userRepository.findByEmail(this.loggedInUser).orElse(null);
            sb.append(String.format("User %s successfully logged out", user.getFullName()));
            this.loggedInUser = "";
        }

        return sb.toString();
    }

    @Override
    public String getLoggedInUser() {
        return loggedInUser;
    }

    @Override
    public String addItem(String title) {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) {
            return sb.append("No logged in user").toString();
        }
        ;

        Game game = this.gameRepository.findByTitle(title).orElse(null);

        if (!isGamePresentByTitle(game, title)) {
            return sb.append(String.format("Game %s does not exist", title)).toString();
        }

        if (user.getGames().contains(game)) {
            return sb.append("User already has game").toString();
        }

        if (this.shoppingCart.contains(game)) {
            return sb.append("Game is already in shopping cart.").toString();
        }

        this.shoppingCart.add(game);
        sb.append(String.format("%s added to cart.", title));

        return sb.toString();
    }

    @Override
    public String removeItem(String title) {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) {
            return sb.append("No logged in user").toString();
        }
        ;

        Game game = this.gameRepository.findByTitle(title).orElse(null);

        if (!isGamePresentByTitle(game, title)) {
            return sb.append(String.format("Game %s does not exist", title)).toString();
        }

        if (!this.shoppingCart.contains(game)) {
            return sb.append("Game not in cart.").toString();
        }

        this.shoppingCart.remove(game);
        sb.append(String.format("%s removed from cart.", title));

        return sb.toString();
    }

    @Override
    public String buyItem() {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) {
            return sb.append("No logged in user").toString();
        };

        sb.append("Successfully bought games: ").append(System.lineSeparator());

        this.shoppingCart.forEach(g -> {
            user.getGames().add(g);
            sb.append(" -").append(g.getTitle()).append(System.lineSeparator());
        });
        this.userRepository.saveAndFlush(user);

        return sb.toString();
    }

    @Override
    public String getOwnedBooks() {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) {
            return sb.append("No logged in user").toString();
        }
        ;

        user.getGames().forEach(g -> sb.append(g.getTitle()).append(System.lineSeparator()));

        return sb.toString();
    }

    private boolean isUserLoggedIn(User user, String loggedInUser) {
        user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        return user != null;
    }

    private boolean isGamePresentByTitle(Game game, String title) {

        Game isPresent = this.gameRepository.findByTitle(title).orElse(null);

        return isPresent != null;
    }

}
