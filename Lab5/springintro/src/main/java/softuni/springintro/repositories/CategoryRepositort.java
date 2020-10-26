package softuni.springintro.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintro.entities.Category;

@Repository
public interface CategoryRepositort extends JpaRepository<Category, Integer> {

}
