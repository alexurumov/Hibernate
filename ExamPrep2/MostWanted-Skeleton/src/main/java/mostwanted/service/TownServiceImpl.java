package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TownServiceImpl implements TownService{

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public TownServiceImpl(TownRepository townRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean townsAreImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder sb = new StringBuilder();

        TownImportDto[] townImportDtos = gson.fromJson(townsFileContent, TownImportDto[].class);

        for (TownImportDto dto : townImportDtos) {
            Town town = mapper.map(dto, Town.class);
            if (!validationUtil.isValid(town)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            Town isPresent = townRepository.findByName(town.getName()).orElse(null);
            if (isPresent != null) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            this.townRepository.saveAndFlush(town);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName())).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String exportRacingTowns() {
        StringBuilder sb = new StringBuilder();

        townRepository.findAll()
                .stream()
                .filter(t -> t.getRacers().size() > 0)
                .sorted((t1, t2) -> {
                    int compare = Integer.compare(t2.getRacers().size(), t1.getRacers().size());

                    if (compare == 0) {
                        return t1.getName().compareTo(t2.getName());
                    }

                    return compare;
                })
                .forEach(t -> sb.append(t.toString()).append(System.lineSeparator()));

        return sb.toString();
    }
}
