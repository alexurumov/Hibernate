package softuni.gamestoredemo2.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.gamestoredemo2.domain.dtos.GameAddDto;
import softuni.gamestoredemo2.domain.dtos.GameDetailsDto;
import softuni.gamestoredemo2.domain.dtos.GameDto;
import softuni.gamestoredemo2.domain.dtos.GameEditDto;
import softuni.gamestoredemo2.domain.entities.Game;
import softuni.gamestoredemo2.domain.entities.Role;
import softuni.gamestoredemo2.domain.entities.User;
import softuni.gamestoredemo2.repositories.GameRepository;
import softuni.gamestoredemo2.repositories.UserRepository;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private ModelMapper mapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository, UserService userService) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.userService = userService;
        this.mapper = new ModelMapper();
    }

    @Override
    public String addGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.userService.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) {
            return sb.append("No logged in user").toString();
        };

        if (!isUserAdmin(user, loggedInUser)) {
            return sb.append(String.format("%s is not Admin", user.getFullName())).toString();
        };

        Game game = this.mapper.map(gameAddDto, Game.class);

        if (!validateGame(sb, game).toString().isEmpty()) {
            return sb.toString();
        }

        if (isGamePresentByTitle(game)) {
            return sb.append("Game already exists").toString();
        };

        this.gameRepository.saveAndFlush(game);
        sb.append(String.format("Added %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public String editGame(GameEditDto gameEditDto) {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.userService.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) { return sb.append("No logged in user").toString(); };

        if (!isUserAdmin(user, loggedInUser)) { return sb.append(String.format("%s is not Admin", user.getFullName())).toString(); };

        Game game = this.gameRepository.findById(gameEditDto.getId()).orElse(null);

        if (!isGamePresentById(game)) { return sb.append(String.format("Game id %d does not exist", gameEditDto.getId())).toString(); }

        //Set new properties
        Arrays.stream(gameEditDto.getParams()).forEach(p -> {
            String[] split = p.split("=");
            String property = split[0];

            switch (property) {
                case "title":
                    game.setTitle(split[1]);
                    break;
                case "trailer":
                    game.setTrailer(split[1]);
                    break;
                case "image_thumbnail":
                    game.setImageThumbnail(split[1]);
                    break;
                case "size":
                    game.setSize(Double.parseDouble(split[1]));
                    break;
                case "price":
                    game.setPrice(new BigDecimal(split[1]));
                    break;
                case "description":
                    game.setDescription(split[1]);
                    break;
                case "release_date":
                    game.setReleaseDate(LocalDate.parse(split[1], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    break;
            }



        });

        if (!validateGame(sb, game).toString().isEmpty()) {
            return sb.toString();
        }

        this.gameRepository.saveAndFlush(game);
        sb.append(String.format("Edited %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public String deleteGame(int id) {
        StringBuilder sb = new StringBuilder();

        String loggedInUser = this.userService.getLoggedInUser();
        User user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        if (!isUserLoggedIn(user, loggedInUser)) { return sb.append("No logged in user").toString(); };

        if (!isUserAdmin(user, loggedInUser)) { return sb.append(String.format("%s is not Admin", user.getFullName())).toString(); };

        Game game = this.gameRepository.findById(id).orElse(null);

        if (!isGamePresentById(game)) { return sb.append(String.format("Game id %d does not exist", id)).toString(); }

        this.gameRepository.delete(game);
        sb.append(String.format("Deleted %s", game.getTitle()));

        return sb.toString();
    }

    @Override
    public List<GameDto> allGames() {

        return this.gameRepository.findAll()
                .stream()
                .map(g -> mapper.map(g, GameDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public GameDetailsDto gameWithDetails(String title) {

        return this.gameRepository.findByTitle(title).map(game -> mapper.map(game, GameDetailsDto.class)).orElse(null);

    }

    private boolean isUserLoggedIn(User user, String loggedInUser) {
        user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        return user != null;
    }

    private boolean isUserAdmin(User user, String loggedInUser) {

        user = this.userRepository.findByEmail(loggedInUser).orElse(null);

        return user.getRole().equals(Role.ADMIN);
    }

    private boolean isGamePresentByTitle(Game game) {

        Game isPresent =  this.gameRepository.findByTitle(game.getTitle()).orElse(null);

        return isPresent != null;
    }

    private boolean isGamePresentById(Game game) {
        return game != null;
    }

    private StringBuilder validateGame(StringBuilder sb, Game game) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Game>> violations = validator.validate(game);

        if (violations.size() > 0) {
            for (ConstraintViolation<Game> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
        }
        return sb;
    }
}
