package softuni.xmlparsingdemo.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import softuni.xmlparsingdemo.domain.dtos.CarDto;
import softuni.xmlparsingdemo.domain.dtos.CarsDto;
import softuni.xmlparsingdemo.domain.dtos.UserDto;
import softuni.xmlparsingdemo.domain.entities.Car;
import softuni.xmlparsingdemo.domain.entities.User;
import softuni.xmlparsingdemo.repositories.CarRepository;
import softuni.xmlparsingdemo.repositories.UserRepository;
import softuni.xmlparsingdemo.services.CarService;
import softuni.xmlparsingdemo.services.UserService;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

@Controller
@Transactional
public class XmlDemoController implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CarService carService;
    private final ModelMapper mapper;

    @Autowired
    public XmlDemoController(UserService userService, UserRepository userRepository, CarRepository carRepository, CarService carService, ModelMapper mapper) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
        this.carService = carService;
        this.mapper = mapper;
    }

    @Override
    public void run(String... args) throws Exception {

        CarsDto carsDto = new CarsDto();
        carsDto.setCarDtos(this.carService.getAllCars());

        UserDto userDto = this.userService.getAllUsers().get(0);
        userDto.setCarsDto(carsDto);



        // DTO -> XML

//        // For 1 dto
//        UserDto user = users.get(0);

//        //For multiple dtos
//
//        List<UserDto> users = this.userService.getAllUsers();
//
//        UsersDto userRepository = new UsersDto();
//
//        users.forEach(u -> userRepository.getUserDtos().add(u));

        // Wrapper example



        JAXBContext jaxbContext = JAXBContext.newInstance(UserDto.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        StringWriter writer = new StringWriter();
//        marshaller.marshal(user, writer);

        marshaller.marshal(userDto, writer);

        System.out.println(writer.toString());

        // XML -> DTO
//        // For 1 dto
//        String xmlUser = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                "<user>\n" +
//                "    <first_name>Hrisi</first_name>\n" +
//                "    <last_name>Korcheva</last_name>\n" +
//                "    <age>22</age>\n" +
//                "</user>";

//        //For multiple dtos
//
//        String xmlUsers = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
//                "<users>\n" +
//                "    <user>\n" +
//                "        <first_name>Alex</first_name>\n" +
//                "        <last_name>Urumov</last_name>\n" +
//                "        <age>45</age>\n" +
//                "    </user>\n" +
//                "    <user>\n" +
//                "        <first_name>Sasho</first_name>\n" +
//                "        <last_name>Georgiev</last_name>\n" +
//                "        <age>23</age>\n" +
//                "    </user>\n" +
//                "    <user>\n" +
//                "        <first_name>Nikolay</first_name>\n" +
//                "        <last_name>Aleksandrov</last_name>\n" +
//                "        <age>12</age>\n" +
//                "    </user>\n" +
//                "</users>";

//        StringReader reader = new StringReader(xmlUsers);
//
//        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//        UsersDto user = (UsersDto) unmarshaller.unmarshal(reader);


    }
}
