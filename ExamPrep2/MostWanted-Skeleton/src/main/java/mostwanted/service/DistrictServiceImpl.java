package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService{

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public DistrictServiceImpl(DistrictRepository districtRepository, TownRepository townRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean districtsAreImported() {
        return districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();

        DistrictImportDto[] districtImportDtos = gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        for (DistrictImportDto dto : districtImportDtos) {
            District district = mapper.map(dto, District.class);

            Town town = townRepository.findByName(dto.getTownName()).orElse(null);

            if (town == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            district.setTown(town);

            if (!validationUtil.isValid(district)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            District isPresent = districtRepository.findByName(district.getName()).orElse(null);

            if (isPresent != null) {
                sb.append(Constants.DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            districtRepository.saveAndFlush(district);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName())).append(System.lineSeparator());

        }

        return sb.toString();
    }
}
