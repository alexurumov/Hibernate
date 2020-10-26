package com.minkov.mappingdemo;

import org.modelmapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements CommandLineRunner {
    @Autowired
    ModelMapper mapper;

    @Override
    public void run(String... args) throws Exception {
        Address address = new Address() {{
            setCity("Sofia");
        }};

        /** this was the problem. This is not a Student object, but a anonymous inheritor of Student
        Student student = new Student() {{
            setId(1);
            setFirstName("Pesho");
            setLastName("Peshov");
            setAddress(address);
        }};
         **/

        Student student = new Student(0, "Pesho", "Peshov", address);
        StudentDto studentDto = mapper.map(student, StudentDto.class);
        System.out.println(studentDto);
    }
}
