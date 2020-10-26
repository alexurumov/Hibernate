package com.example.springdatademo.services;

import com.example.springdatademo.entities.Student;
import com.example.springdatademo.repositories.StudentRepository;
import com.example.springdatademo.services.base.StudentsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentsServiceImpl implements StudentsService {

    private final StudentRepository studentRepository;

    public StudentsServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Student> getAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> findByNamePattern(String namePattern) {
        return studentRepository.findAll()
                .stream()
                .filter(student -> student.getName().contains(namePattern))
                .collect(Collectors.toList());
    }
}
