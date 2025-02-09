package Homework.Spring.repository;

import org.springframework.stereotype.Repository;

import Homework.Spring.entity.User;

@Repository
public class DemoRepository {
    private User demoUser ;

    public DemoRepository() {
        demoUser = new User();
        demoUser.setLogin("Andrey");
        demoUser.setPassword("123");
    }

    public User getDemoUser () {
        return demoUser ;
    }
}
