package softuni.jsonexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonexercise.domain.entities.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

@Query("SELECT c FROM Category c INNER JOIN c.products GROUP BY c.id")
    List<Category> findCategoriesByProductsCount();
}
