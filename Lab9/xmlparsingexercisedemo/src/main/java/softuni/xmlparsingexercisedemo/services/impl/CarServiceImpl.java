package softuni.xmlparsingexercisedemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.xmlparsingexercisedemo.domain.dtos.*;
import softuni.xmlparsingexercisedemo.domain.entities.Car;
import softuni.xmlparsingexercisedemo.domain.entities.Part;
import softuni.xmlparsingexercisedemo.repositories.CarRepository;
import softuni.xmlparsingexercisedemo.repositories.PartRepository;
import softuni.xmlparsingexercisedemo.services.CarService;
import softuni.xmlparsingexercisedemo.util.FileUtil;
import softuni.xmlparsingexercisedemo.util.ValidationUtil;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@Transactional
public class CarServiceImpl implements CarService {

    private static final String CARS_FILE_PATH =
            "/Users/macbookair/Downloads/Hibernate/Lab9/xmlparsingexercisedemo/src/main/resources/files/cars.xml";

    private final CarRepository carRepository;
    private final PartRepository partRepository;
    private final FileUtil fileUtil;
    private final ModelMapper mapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, PartRepository partRepository, FileUtil fileUtil, ModelMapper mapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.partRepository = partRepository;
        this.fileUtil = fileUtil;
        this.mapper = mapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public void importCars() throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(CarsDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        String s = fileUtil.fileContent(CARS_FILE_PATH);
        StringReader reader = new StringReader(s);
        CarsDto carsDto = (CarsDto) unmarshaller.unmarshal(reader);

        List<Car> cars = carsDto.getCarDtoList()
                .stream()
                .map(dto -> mapper.map(dto, Car.class))
                .collect(Collectors.toList());

        for (Car car : cars) {
            if (!validationUtil.isValid(car)) {
                System.out.println(validationUtil.violations(car));

                continue;
            }

            car.setParts(getRandomParts());
            this.carRepository.saveAndFlush(car);
        }

    }

    @Override
    public String exportCarsByMake() throws JAXBException {

        List<CarExportDto> toyota = this.carRepository.findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(c -> mapper.map(c, CarExportDto.class))
                .collect(Collectors.toList());

        CarsExportDto carsExportDto = new CarsExportDto();
        carsExportDto.setCarExportDtos(toyota);

        JAXBContext jaxbContext = JAXBContext.newInstance(CarsExportDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(carsExportDto, writer);

        return writer.toString();
    }

    @Override
    public String exportCarsWithParts() throws JAXBException {

        List<CarWithPartsDto> carDtos = this.carRepository.findAll()
                .stream()
                .map(car -> {
                    CarWithPartsDto dto = mapper.map(car, CarWithPartsDto.class);
                    List<PartExportDto> partDtos = car.getParts().stream().map(p -> mapper.map(p, PartExportDto.class)).collect(Collectors.toList());
                    PartExportMainDto partExportMainDto = new PartExportMainDto();
                    partExportMainDto.setPartExportDtos(partDtos);
                    dto.setPartExportMainDto(partExportMainDto);
                    return dto;
                })
                .collect(Collectors.toList());

        CarWithPartsMainDto mainDto = new CarWithPartsMainDto();
        mainDto.setCarWithPartsDtos(carDtos);

        JAXBContext jaxbContext = JAXBContext.newInstance(CarWithPartsMainDto.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
        marshaller.marshal(mainDto, writer);

        return writer.toString();
    }

    private List<Part> getRandomParts() {

        Random random = new Random();
        int randomSize = random.nextInt(11) + 10;

        List<Part> parts = new ArrayList<>();

        for (int i = 0; i < randomSize; i++) {
            int randomId = random.nextInt((int) this.partRepository.count()) + 1;
            Part part = this.partRepository.findById(randomId).orElse(null);

            parts.add(part);
        }

        return parts;
    }
}
