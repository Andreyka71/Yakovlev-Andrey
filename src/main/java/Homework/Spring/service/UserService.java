package Homework.Spring.service;

import Homework.Spring.dto.request.UserRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.UserResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.TelegramToken;
import Homework.Spring.entity.User;
import Homework.Spring.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UsersRepository userRepository;
    private final DeviceService deviceService;

    public UserResponse registration(UserRequest userRequest) {
        User user = buildUserRequest(userRequest);
        if (!checkUserExistence(user)) {
            return buildUserResponse(userRepository.addUser(user));
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь уже существует");
        }
    }

    public List<DeviceResponse> listOfDevicesOfUser(UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userRequest.getId());
        User user = new User();

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        UserResponse userResponse = buildUserResponse(user);
        System.out.println(userResponse.getDevices());
        return userResponse.getDevices();
    }

    public UserResponse updateUser(UserRequest userRequest) {
        Optional<User> optionalUser  = userRepository.findById(userRequest.getId());
        
        if (!optionalUser .isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    
        User user = optionalUser .get();
        if (userRequest.getLogin() != null) {
            user.setLogin(userRequest.getLogin());
        }
    
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }

        if (userRequest.getTelegramToken() != null) {
            TelegramToken telegramToken = new TelegramToken();
            telegramToken.setUserId(user.getId());
            telegramToken.setToken(userRequest.getTelegramToken());
            user.setTelegramToken(telegramToken);
        }
        return buildUserResponse(user);
    }
    
    public UserResponse fullUpdateUser(UserRequest userRequest) {
        Optional<User> optionalUser  = userRepository.findById(userRequest.getId());
        
        if (!optionalUser .isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        User user = optionalUser .get();
        if (userRequest.getLogin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть логин");
        }
    
        if (userRequest.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть пароль");
        }

        if (userRequest.getTelegramToken() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть телеграм токен");
        }
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setUserId(user.getId());
        telegramToken.setToken(userRequest.getTelegramToken());
        user.setTelegramToken(telegramToken);
        return buildUserResponse(user);
    }

    private UserResponse buildUserResponse(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setLogin(user.getLogin());
        userResponse.setPassword(user.getPassword());
        userResponse.setTelegramToken(user.getTelegramToken().getToken());

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        List<Device> devices = user.getDevices();

        for (Device device : devices) {
            devicesResponse.add(deviceService.buildDeviceResponse(device));
        }
        userResponse.setDevices(devicesResponse);
        return userResponse;
    }

    private User buildUserRequest(UserRequest userRequest) {
        User user = new User();
        user.setId(userRequest.getId());
        user.setLogin(userRequest.getLogin());
        user.setPassword(userRequest.getPassword());
        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setToken(userRequest.getTelegramToken());
        telegramToken.setUserId(user.getId());
        user.setTelegramToken(telegramToken);
        return user;
    }
    
    private boolean checkUserExistence(User user) {
        return userRepository.existsById(user.getId());
    }
}
