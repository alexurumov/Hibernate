package softuni.jsonexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.jsonexercise.domain.entities.Product;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByPriceBetweenAndBuyerIsNullOrderByPrice(BigDecimal lowerBound, BigDecimal upperBound);
}
