package softuni.xmlparsingdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.xmlparsingdemo.domain.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
