package softuni.gamestoredemo2.services;

import softuni.gamestoredemo2.domain.dtos.GameAddDto;
import softuni.gamestoredemo2.domain.dtos.GameDetailsDto;
import softuni.gamestoredemo2.domain.dtos.GameDto;
import softuni.gamestoredemo2.domain.dtos.GameEditDto;

import java.util.List;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    String editGame(GameEditDto gameEditDto);

    String deleteGame(int id);

    List<GameDto> allGames();

    GameDetailsDto gameWithDetails(String title);
}
