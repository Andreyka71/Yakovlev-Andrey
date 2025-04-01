package Homework.Spring.repository;

import Homework.Spring.entity.Device;
import Homework.Spring.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RulesRepository extends JpaRepository<Rule, Integer> {

    Optional<Rule> findByRuleAndDevice(String rule, Device device);
}