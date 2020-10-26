package com.example.springdatademo;

import com.example.springdatademo.repositories.StudentRepository;
import com.example.springdatademo.services.base.StudentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class App implements CommandLineRunner {
    @Autowired
    private StudentsService studentsService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("It works!");
        studentsService.getAll()
                .forEach(System.out::println);
    }
}
