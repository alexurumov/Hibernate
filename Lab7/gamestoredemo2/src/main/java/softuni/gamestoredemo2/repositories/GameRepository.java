package softuni.gamestoredemo2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.gamestoredemo2.domain.entities.Game;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Optional<Game> findByTitle(String title);
    Optional<Game> findById(int id);
}
