package com.example.springdatademo.services.base;

import com.example.springdatademo.entities.Student;

import java.util.List;

public interface StudentsService {
    List<Student> getAll();

    List<Student> findByNamePattern(String namePattern);

}
