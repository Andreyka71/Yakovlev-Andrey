package Homework.Spring.repository;

import Homework.Spring.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<User, Integer> {

    @Transactional(timeout = 5)
    boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
