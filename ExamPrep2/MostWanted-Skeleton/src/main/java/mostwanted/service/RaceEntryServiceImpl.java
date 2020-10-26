package mostwanted.service;

import mostwanted.common.Constants;
import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import mostwanted.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final RacerRepository racerRepository;
    private final CarRepository carRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, RacerRepository racerRepository, CarRepository carRepository, FileUtil fileUtil, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper mapper) {
        this.raceEntryRepository = raceEntryRepository;
        this.racerRepository = racerRepository;
        this.carRepository = carRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean raceEntriesAreImported() {
        return raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
        return fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        RaceEntryImportRootDto raceEntryImportRootDto = xmlParser.parseXml(RaceEntryImportRootDto.class, RACE_ENTRIES_XML_FILE_PATH);

        for (RaceEntryImportDto dto : raceEntryImportRootDto.getRaceEntryImportDtos()) {
            RaceEntry raceEntry = new RaceEntry();

            raceEntry.setHasFinished(dto.getHasFinished());
            raceEntry.setFinishTime(dto.getFinishTime());

            Car car = carRepository.findById(dto.getCarId()).orElse(null);

            if (car == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            raceEntry.setCar(car);

            Racer racer = racerRepository.findByName(dto.getRacer()).orElse(null);

            if (racer == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            raceEntry.setRacer(racer);

            if (!validationUtil.isValid(raceEntry)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            raceEntryRepository.saveAndFlush(raceEntry);
            sb.append(String.format(Constants.SUCCESSFUL_IMPORT_MESSAGE,raceEntry.getClass().getSimpleName(), raceEntry.getId())).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
