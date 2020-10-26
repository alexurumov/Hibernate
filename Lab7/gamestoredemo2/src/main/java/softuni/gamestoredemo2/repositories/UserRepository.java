package softuni.gamestoredemo2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import softuni.gamestoredemo2.domain.entities.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
