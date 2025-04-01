package Homework.Spring.repository;

import Homework.Spring.entity.TelegramToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TelegramTokenRepository extends JpaRepository<TelegramToken, Integer> {

}
