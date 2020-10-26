package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public RacerServiceImpl(RacerRepository racerRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.racerRepository = racerRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean racersAreImported() {
        return racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder sb = new StringBuilder();

        RacerImportDto[] racerImportDtos = gson.fromJson(racersFileContent, RacerImportDto[].class);

        for (RacerImportDto dto : racerImportDtos) {
            Racer racer = mapper.map(dto, Racer.class);

            Town town = townRepository.findByName(dto.getHomeTown()).orElse(null);

            if (town == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            racer.setHomeTown(town);

            if (!validationUtil.isValid(racer)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            Racer isPresent = racerRepository.findByName(racer.getName()).orElse(null);

            if (isPresent != null) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            racerRepository.saveAndFlush(racer);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,racer.getClass().getSimpleName(), racer.getName())).append(System.lineSeparator());

        }

        return sb.toString();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();

        racerRepository.findAll()
                .stream()
                .filter(r -> r.getCars().size() > 0)
                .sorted((r1, r2) -> {
                    int compare = Integer.compare(r2.getCars().size(), r1.getCars().size());

                    if (compare == 0) {
                        return r1.getName().compareTo(r2.getName());
                    }

                    return compare;
                })
                .forEach(r -> sb.append(r.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
