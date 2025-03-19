package Homework.Spring.repository;

import org.springframework.stereotype.Repository;

import Homework.Spring.entity.Rule;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RulesRepository {
    private final Map<Long, Rule> rules = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Rule addRule(Rule rule) {
        Long id = idCounter.incrementAndGet();
        rule.setId(id);
        rules.put(id, rule);
        // логирование
        return rule;
    }

    public Optional<Rule> findById(Long id) {
        return Optional.ofNullable(rules.get(id));
    }

    public void deleteRule(Long id) {
        rules.remove(id);
        // логирование
    }

    public boolean existsById(Long id) {
        return rules.containsKey(id);
    }
}

