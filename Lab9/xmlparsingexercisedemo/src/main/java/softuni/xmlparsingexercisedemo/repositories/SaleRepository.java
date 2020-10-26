package softuni.xmlparsingexercisedemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.xmlparsingexercisedemo.domain.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Integer> {
}
