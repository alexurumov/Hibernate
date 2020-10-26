package com.minkov.mappingdemo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    ModelMapper mapper;

    @GetMapping("/test")
    public StudentDto get() {
        Address address = new Address() {{
            setCity("Sofia");
        }};

        StudentDto studentDto = mapper.map(new Student() {{
            setId(1);
            setFirstName("Pesho");
            setLastName("Peshov");
            setAddress(address);
        }}, StudentDto.class);
        System.out.println(studentDto);
        return studentDto;
    }
}
