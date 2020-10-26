package softuni.xmlparsingexercisedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.xmlparsingexercisedemo.domain.dtos.CustomerDto;
import softuni.xmlparsingexercisedemo.domain.entities.Customer;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findAllByOrderByBirthDateAsc();
}
