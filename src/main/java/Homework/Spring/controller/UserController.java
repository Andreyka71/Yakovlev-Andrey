package Homework.Spring.controller;

import Homework.Spring.dto.request.UserRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.UserResponse;
import Homework.Spring.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RateLimiter;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("UserControllerCircuitBreaker");
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("UserControllerRateLimiter");

    @PostMapping("/registration")
    public UserResponse registration(@RequestBody UserRequest userRequest) {
        return userService.registration(userRequest);
    }

    @GetMapping("/{login}/devices")
    public List<DeviceResponse> listOfDevicesOfUser(@PathVariable String login) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            UserRequest userRequest = new UserRequest();
            userRequest.setLogin(login);
            return userService.listOfDevicesOfUser(userRequest);
        }));
    }

    @PutMapping("/update/{login}")
    public UserResponse updateRule(@PathVariable String login, @RequestBody UserRequest userRequest) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            userRequest.setLogin(login);
            return userService.updateUser(userRequest);
        }));
    }

    @PatchMapping("/fullUpdate/{login}")
    public UserResponse fullUpdateRule(@PathVariable String login, @RequestBody UserRequest userRequest) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            userRequest.setLogin(login);
            return userService.fullUpdateUser(userRequest);
        }));
    }
}