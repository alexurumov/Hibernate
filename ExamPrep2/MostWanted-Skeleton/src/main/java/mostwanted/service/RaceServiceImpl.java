package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.EntryImportRootDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.*;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final RaceEntryRepository raceEntryRepository;
    private final DistrictRepository districtRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public RaceServiceImpl(RaceRepository raceRepository, RaceEntryRepository raceEntryRepository, DistrictRepository districtRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper mapper) {
        this.raceRepository = raceRepository;
        this.raceEntryRepository = raceEntryRepository;
        this.districtRepository = districtRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean racesAreImported() {
        return raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceImportRootDto raceImportRootDto = xmlParser.parseXml(RaceImportRootDto.class, RACES_XML_FILE_PATH);

        for (RaceImportDto raceDto : raceImportRootDto.getRaceImportDtos()) {
            Race race = mapper.map(raceDto, Race.class);

            District district = districtRepository.findByName(raceDto.getDistrictName()).orElse(null);

            if (district == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            race.setDistrict(district);

            EntryImportRootDto entryImportRootDto = mapper.map(raceDto.getEntries(), EntryImportRootDto.class);

            List<RaceEntry> entries = new ArrayList<>();

            for (EntryImportDto entryDto : entryImportRootDto.getEntryImportDtos()) {
                RaceEntry raceEntry = this.raceEntryRepository.findById(entryDto.getId()).orElse(null);

                if (raceEntry == null) {
                    break;
                }

                entries.add(raceEntry);
            }

            if (entryImportRootDto.getEntryImportDtos().size() != entries.size()) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            race.setEntries(entries);

            if (!validationUtil.isValid(race)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            raceRepository.saveAndFlush(race);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,
                    race.getClass().getSimpleName(), race.getId())).append(System.lineSeparator());

            entries.forEach(e -> {
                e.setRace(race);
                raceEntryRepository.saveAndFlush(e);
            });
        }

        return sb.toString();
    }
}