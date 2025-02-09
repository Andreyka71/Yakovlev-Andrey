package Homework.Spring.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Homework.Spring.entity.User;
import Homework.Spring.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @PostMapping("/registration")
    public String registration(@RequestBody User user) {
        return userService.registration();
    }

    @PostMapping("/entry")
    public String entry(@RequestBody User user) {
        return userService.entry();
    }
}