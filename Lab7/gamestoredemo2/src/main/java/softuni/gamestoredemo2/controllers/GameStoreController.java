package softuni.gamestoredemo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.gamestoredemo2.domain.dtos.*;
import softuni.gamestoredemo2.services.GameService;
import softuni.gamestoredemo2.services.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private final UserService userService;
    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {

        Scanner in = new Scanner(System.in);

        while (true) {
            String[] inputParams = in.nextLine().split("\\|");

            switch (inputParams[0]) {
                case "RegisterUser":

                    UserRegisterDto userRegisterDto = new UserRegisterDto(inputParams[1], inputParams[2], inputParams[3], inputParams[4]);

                    System.out.println(this.userService.registerUser(userRegisterDto));

                    break;

                case "LoginUser":

                    UserLoginDto userLoginDto = new UserLoginDto(inputParams[1], inputParams[2]);

                    System.out.println(this.userService.loginUser(userLoginDto));

                    break;

                case "Logout":

                    System.out.println(this.userService.logoutUser());

                    break;

                case "AddGame":

                    GameAddDto gameAddDto = new GameAddDto(
                            inputParams[1],
                            inputParams[4],
                            inputParams[5],
                            Double.parseDouble(inputParams[3]),
                            new BigDecimal(inputParams[2]),
                            inputParams[6],
                            LocalDate.parse(inputParams[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));

                    System.out.println(this.gameService.addGame(gameAddDto));

                    break;

                case "EditGame":

                    String[] params = Arrays.stream(inputParams).skip(2).toArray(String[]::new);

                    GameEditDto gameEditDto = new GameEditDto(Integer.parseInt(inputParams[1]), params);

                    System.out.println(this.gameService.editGame(gameEditDto));

                    break;

                case "DeleteGame":

                    int id = Integer.parseInt(inputParams[1]);

                    System.out.println(this.gameService.deleteGame(id));

                    break;

                case "AllGames":

                    this.gameService.allGames().forEach(System.out::println);

                    break;

                case "DetailGame":

                    String title = inputParams[1];

                    GameDetailsDto dto = this.gameService.gameWithDetails(title);

                    if (dto != null) {
                        System.out.println(dto);
                    } else {
                        System.out.println("No such game");
                    }

                    break;

                case "OwnedGames":

                    System.out.println(this.userService.getOwnedBooks());

                    break;

                case "AddItem":

                    String itemTitle = inputParams[1];

                    System.out.println(this.userService.addItem(itemTitle));

                    break;

                case "RemoveItem":

                    String removeItemTitle = inputParams[1];

                    System.out.println(this.userService.removeItem(removeItemTitle));

                    break;

                case "BuyItem":

                    System.out.println(this.userService.buyItem());

                    break;
            }
        }

    }
}
