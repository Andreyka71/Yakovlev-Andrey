package Homework.Spring.controller;

import Homework.Spring.controller.OpenApi.UserApi;
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
public class UserController implements UserApi{
    private final UserService userService;
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("UserControllerCircuitBreaker");
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("UserControllerRateLimiter");

    @PostMapping("/registration")
    @Override
    public UserResponse registration(@RequestBody UserRequest userRequest) {
        return userService.registration(userRequest);
    }

    @GetMapping("/{id}/devices")
    @Override
    public List<DeviceResponse> listOfDevicesOfUser(@PathVariable long id) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            UserRequest userRequest = new UserRequest();
            userRequest.setId(id);
            return userService.listOfDevicesOfUser(userRequest);
        }));
    }

    @PutMapping("/update/{id}")
    @Override
    public UserResponse updateRule(@PathVariable long id, @RequestBody UserRequest userRequest) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            userRequest.setId(id);
            return userService.updateUser(userRequest);
        }));
    }

    @PatchMapping("/fullUpdate/{id}")
    @Override
    public UserResponse fullUpdateRule(@PathVariable long id, @RequestBody UserRequest userRequest) {
        return circuitBreaker.executeSupplier(() -> rateLimiter.executeSupplier(() -> {
            userRequest.setId(id);
            return userService.fullUpdateUser(userRequest);
        }));
    }
}