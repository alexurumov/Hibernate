package softuni.modelmapperdemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import softuni.modelmapperdemo.dtos.EmployeeDto;
import softuni.modelmapperdemo.dtos.ManagerDto;
import softuni.modelmapperdemo.entities.Employee;

//@Configuration //Set Up Configurations of Mapper, so we can inject in in our CommandLineRunner or other classes
public class MappingCongiguration {

//    static ModelMapper modelMapper;

//    static {
//        modelMapper = new ModelMapper();
//
//        modelMapper.createTypeMap(Employee.class, ManagerDto.class)
//                .addMappings(
//                        m -> m.map(
//                                src -> src.getEmployees().size(),
//                                ManagerDto::setCountOfEmployees));
//
//        modelMapper.validate();
//    }

    ////        For Method 3 in App:
//        mapper
//                .createTypeMap(Student.class, StudentDto.class)
//                .addMappings(
//                        m -> m.map(
//                                src -> src.getAddress().getCity(),
//                                StudentDto::setCity
//                        )
//                );

//        ModelMapper mapper = new ModelMapper();
//
//    @Bean // Must have @
//    public ModelMapper mapper() {
//        return modelMapper;
//    }
}
