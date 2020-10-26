package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.common.Constants;
import softuni.exam.domain.dtos.players.PlayerDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

@Service
public class PlayerServiceImpl implements PlayerService {

    private static final String PLAYERS_JSON_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Exam/Football-Info_Skeleton/src/main/resources/files/json/players.json";

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper mapper;
    private final ValidatorUtil validatorUtil;

    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, Gson gson, ModelMapper mapper, ValidatorUtil validatorUtil) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.mapper = mapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();

        FileReader reader = new FileReader(PLAYERS_JSON_FILE_PATH);

        PlayerDto[] playerDtos = gson.fromJson(reader, PlayerDto[].class);

        for (PlayerDto playerDto : playerDtos) {
            Player player = new Player();

            player.setFirstName(playerDto.getFirstName());
            player.setLastName(playerDto.getLastName());
            player.setNumber(playerDto.getNumber());
            player.setSalary(playerDto.getSalary());

            try {
                Position position = Position.valueOf(playerDto.getPosition());
                player.setPosition(position);
            } catch (IllegalArgumentException e) {
                sb.append(String.format(Constants.INVALID_INPUT, player.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            Picture picture = pictureRepository.findByUrl(playerDto.getPicture().getUrl()).orElse(null);

            if (picture == null) {
                sb.append(String.format(Constants.INVALID_INPUT, player.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            player.setPicture(picture);

            Team team = teamRepository.findByName(playerDto.getTeam().getName()).orElse(null);

            if (team == null) {
                sb.append(String.format(Constants.INVALID_INPUT, player.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            player.setTeam(team);

            if (!validatorUtil.isValid(player)) {
                sb.append(String.format(Constants.INVALID_INPUT, player.getClass().getSimpleName())).append(System.lineSeparator());

                continue;
            }

            playerRepository.saveAndFlush(player);
            sb.append(String.format(Constants.SUCCESSFUL_INPUT, player.getClass().getSimpleName(), String.format("%s %s", player.getFirstName(), player.getLastName()))).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() > 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return fileUtil.readFile(PLAYERS_JSON_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        playerRepository.findAll()
                .stream()
                .filter(p -> p.getSalary().compareTo(BigDecimal.valueOf(100000)) > 0)
                .sorted((p1, p2) -> p2.getSalary().compareTo(p1.getSalary()))
                .forEach(p -> {
                    sb.append("Player name: ").append(p.getFirstName()).append(" ").append(p.getLastName()).append(System.lineSeparator())
                            .append("\t").append("Number: ").append(p.getNumber()).append(System.lineSeparator())
                            .append("\t").append("Salary: ").append(p.getSalary()).append(System.lineSeparator())
                            .append("\t").append("Team: ").append(p.getTeam().getName()).append(System.lineSeparator());
                });

        return sb.toString();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        sb.append("Team: North Hub").append(System.lineSeparator());

        playerRepository.findAll()
                .stream()
                .filter(p -> p.getTeam().getName().equals("North Hub"))
                .forEach(p -> sb.append(p.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
