package Homework.Spring.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import Homework.Spring.entity.Device;
import Homework.Spring.entity.User;

@Repository
public class UsersRepository {

    private final Map<Long, User> users = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);
    
    public User addUser(User user) {
        Long id = idCounter.incrementAndGet();
        user.setId(id);
        user.setDevices(new ArrayList<Device>());
        users.put(id, user);
        //  логирование
        return user;
    }

    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }

    public void deleteUser(Long id) {
        users.remove(id);
        //  логирование
    }
    
    public boolean existsById(Long id) {
        return users.containsKey(id);
    }
}
