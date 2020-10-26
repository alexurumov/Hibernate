package softuni.modelmapperdemo;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.modelmapperdemo.dtos.EmployeeDto;
import softuni.modelmapperdemo.entities.Address;
import softuni.modelmapperdemo.entities.Employee;
import softuni.modelmapperdemo.repositories.EmployeeRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class App implements CommandLineRunner {

//    @Autowired // Injection of mapper, configured in .config/MappingConfiguration class
//    ModelMapper mapper;

    @Autowired
    private final EmployeeRepository employeeRepository;

    public App(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) throws Exception {

//        Student student = new Student();
//        student.setId(1);
//        student.setFirstName("Pesho");
//        student.setLastName("Peshov");
//
//        Address address = new Address();
//        address.setCity("Sofia");
//        student.setAddress(address);

////        Converter example 1:
//        Converter<String, String> stringConverter = new AbstractConverter<String, String>() {
//            @Override
//            protected String convert(String s) {
//                return s == null ? null : s.toUpperCase();
//            }
//        };
//
////        Mapper Version 1:
//        PropertyMap<Student, StudentDto> studentMap = new PropertyMap<Student, StudentDto>() {
//            @Override
//            protected void configure() {
//                map().setCity(source.getAddress().getCity()); // Set (map) property
//                using(stringConverter).map().setFirstName(source.getFirstName()); // Converter usage example
//                skip().setSalary(null); // Skip (not map) property
//            }
//        };
//
//        StudentDto studentDto = mapper.addMappings(studentMap).map(student);


//        Converter example 2:
//        Converter<Address, String> employeeToNameConv = new AbstractConverter<Address, String>() {
//            @Override
//            protected String convert(Address address) {
//                return address == null
//                        ? ""
//                        : address.getCity();
//            }
//        };


////        Mapper Version 2:
//        TypeMap<Student, StudentDto> typeMap = mapper.createTypeMap(Student.class, StudentDto.class);
//
//        typeMap.addMappings(
//                m -> {
//                    m.map(src -> src.getAddress().getCity(),
//                            StudentDto::setCity), // Set (map) property
//
//                            m.skip(StudentDto::setSalary),  // Skip (not map) property
//
//                    m.using(employeeToNameConv).map(src -> src.getAddress().getCity()
//                            , StudentDto::setCity); // Converter usage example
//                }
//        );
//
//        StudentDto studentDto = typeMap.map(student);


////        Mapper Version 3 with method below:
//        StudentDto studentDto = mapper.map(student, StudentDto.class);


//        // Full Name And Address Mapping with Converter:
//        Converter<Student, String> studentToFullNameConv = new AbstractConverter<Student, String>() {
//            @Override
//            protected String convert(Student student) {
//                return student == null
//                        ? null
//                        : student.getFirstName() + " " + student.getLastName();
//            }
//        };
//
//        TypeMap<Student, StudentDto> typeMap = mapper.createTypeMap(Student.class, StudentDto.class);
//
//        typeMap.addMappings(
//                m -> m.using(employeeToNameConv)
//                        .map(Student::getAddress, StudentDto::setCity))
//                .addMappings(
//                        m -> m.using(studentToFullNameConv)
//                                .map(src -> src,
//                                        StudentDto::setFullName)
//                );
//
//        StudentDto studentDto = typeMap.map(student);
//        System.out.println(studentDto);

//        // Checks if we have unmapped (or not skipped) properties
//        mapper.validate();
        Employee employee3 = new Employee();
        employee3.setFirstName("Steve");
        employee3.setLastName("Jobbsen");
        employee3.setSalary(BigDecimal.valueOf(2000.21));
        employee3.setBirthday(LocalDate.parse("1989-12-24"));

        this.employeeRepository.saveAndFlush(employee3);

        Employee employee1 = new Employee();
        employee1.setFirstName("Stephen");
        employee1.setLastName("Bjorn");
        employee1.setSalary(BigDecimal.valueOf(4300.00));
        employee1.setBirthday(LocalDate.parse("1989-12-24"));
        employee1.setManager(employee3);

        this.employeeRepository.saveAndFlush(employee1);

        Employee employee2 = new Employee();
        employee2.setFirstName("Kirilyc");
        employee2.setLastName("Lefi");
        employee2.setSalary(BigDecimal.valueOf(4400.00));
        employee2.setBirthday(LocalDate.parse("1989-12-24"));

        employee2.setManager(employee3);

        this.employeeRepository.saveAndFlush(employee2);

        ModelMapper mapper = new ModelMapper();
        TypeMap<Employee, EmployeeDto> typeMap = mapper.createTypeMap(Employee.class, EmployeeDto.class);
        typeMap.addMappings(
                m -> m.map(src -> src.getManager().getLastName(), EmployeeDto::setManager)
        );

        mapper.validate();

        this.employeeRepository.findAllByBirthdayBefore(LocalDate.parse("1990-01-01"))
                .stream()
                .sorted((e1, e2) -> e2.getSalary().compareTo(e1.getSalary()))
                .forEach(e -> System.out.println(typeMap.map(e)));
        

    }
}
