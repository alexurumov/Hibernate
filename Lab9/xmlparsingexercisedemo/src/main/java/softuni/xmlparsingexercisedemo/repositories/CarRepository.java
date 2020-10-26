package softuni.xmlparsingexercisedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.xmlparsingexercisedemo.domain.entities.Car;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
    List<Car> findAllByMakeOrderByModelAscTravelledDistanceDesc(String make);
}
