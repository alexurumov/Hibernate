package softuni.xmlparsingdemo.services;

import softuni.xmlparsingdemo.domain.dtos.CarDto;

import java.util.List;

public interface CarService {

    List<CarDto> getAllCars();
}
