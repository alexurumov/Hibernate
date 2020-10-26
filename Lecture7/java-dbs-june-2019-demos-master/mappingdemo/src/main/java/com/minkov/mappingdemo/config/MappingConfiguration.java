package com.minkov.mappingdemo.config;

import com.minkov.mappingdemo.Address;
import com.minkov.mappingdemo.Student;
import com.minkov.mappingdemo.StudentDto;
import org.modelmapper.*;
import org.modelmapper.config.Configuration.AccessLevel;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MappingConfiguration {
    static ModelMapper modelMapper;

    static {
        modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Student.class, StudentDto.class)
                .addMapping(
                        src -> src.getAddress().getCity(),
                        StudentDto::setNameOfCity
                );

        modelMapper.validate();
    }

    @Bean
    public ModelMapper mapper() {
        return modelMapper;
    }
}
