package softuni.xmlparsingdemo.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.xmlparsingdemo.domain.dtos.CarDto;
import softuni.xmlparsingdemo.repositories.CarRepository;
import softuni.xmlparsingdemo.services.CarService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ModelMapper mapper;

    public CarServiceImpl(CarRepository carRepository, ModelMapper mapper) {
        this.carRepository = carRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CarDto> getAllCars() {
        return this.carRepository.findAll().stream().map(c -> mapper.map(c, CarDto.class)).collect(Collectors.toList());
    }
}
