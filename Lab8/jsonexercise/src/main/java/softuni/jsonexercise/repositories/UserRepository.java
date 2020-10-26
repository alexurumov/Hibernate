package softuni.jsonexercise.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.jsonexercise.domain.entities.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u inner join fetch u.productsSold p where p.buyer is not null group by u.id order by u.lastName, u.firstName")
    List<User> getAllUsersSoldProducts();

    List<User> getAllByProductsSoldNotNullOrderByProductsSoldDesc();
}
