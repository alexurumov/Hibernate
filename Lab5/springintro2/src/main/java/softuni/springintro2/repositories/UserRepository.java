package softuni.springintro2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.springintro2.entities.Deleted;
import softuni.springintro2.entities.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByEmailContaining(String emailProvider);
    List<User> findAllByLastTimeLoggedInBefore(LocalDateTime timeLimit);
    List<User> findAllByIsDeleted(Deleted isDeleted);
}
