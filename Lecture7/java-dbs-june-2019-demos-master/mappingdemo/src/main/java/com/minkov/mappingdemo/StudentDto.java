package com.minkov.mappingdemo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StudentDto {
    private String firstName;
    private String lastName;
    private String nameOfCity;

    @Override
    public String toString() {
        return "StudentDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nameOfCity='" + nameOfCity + '\'' +
                '}';
    }
}
