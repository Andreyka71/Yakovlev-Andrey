package Homework.Spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Homework.Spring.entity.User;
import Homework.Spring.repository.DemoRepository;

@RestController
@RequestMapping("/demo")
public class DemoController {
    private final DemoRepository demoRepository = new DemoRepository();

   @GetMapping("user")
   public User getUser() {
       return demoRepository.getDemoUser();
   }
}