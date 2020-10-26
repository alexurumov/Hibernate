package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.common.Constants;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CarServiceImpl implements CarService{

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final ModelMapper mapper;

    public CarServiceImpl(CarRepository carRepository, RacerRepository racerRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.mapper = mapper;
    }

    @Override
    public Boolean carsAreImported() {
        return carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder sb = new StringBuilder();

        CarImportDto[] carImportDtos = gson.fromJson(carsFileContent, CarImportDto[].class);

        for (CarImportDto dto : carImportDtos) {
            Car car = mapper.map(dto, Car.class);

            Racer racer = racerRepository.findByName(dto.getRacerName()).orElse(null);

            if (racer == null) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            car.setRacer(racer);

            if (!validationUtil.isValid(racer)) {
                sb.append(Constants.INCORRECT_DATA_MESSAGE).append(System.lineSeparator());

                continue;
            }

            carRepository.saveAndFlush(car);
            sb.append(String.format(
                    Constants.SUCCESSFUL_IMPORT_MESSAGE, car.getClass().getSimpleName(),
                    String.format("%s %s @ %s",
                            car.getBrand(),
                            car.getModel(),
                            car.getYearOfProduction()
                    )))
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}
