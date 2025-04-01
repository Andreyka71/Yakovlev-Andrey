package Homework.Spring.service;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import Homework.Spring.dto.request.UserRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.UserResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.TelegramToken;
import Homework.Spring.entity.User;
import Homework.Spring.exeption.CustomException;
import Homework.Spring.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UsersRepository userRepository;
    private final DeviceService deviceService;

    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED,
        rollbackFor = { ResponseStatusException.class, CustomException.class }
    )
    public UserResponse registration(@NotNull UserRequest request) {
        User user = buildUserRequest(request);
        if (!checkUserExistence(user)) {
            userRepository.save(user);
            log.info("Пользователь \"" + request.getLogin() + "\" сохранен");
            return buildUserResponse(user);
        } else {
            log.error("Пользователь \"" + request.getLogin() + "\" уже существует в базе данных");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Пользователь уже существует");
        }
    }

    @Transactional(readOnly = true)
    public UserResponse entry(@NotNull UserRequest request) {
        User user = buildUserRequest(request);
        if (checkUserExistence(user)) {
            return buildUserResponse(user);
        } else {
            log.error("Пользователь \"" + request.getLogin() + "\" не найден в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }
    }

    @Transactional(readOnly = true)
    public List<DeviceResponse> listOfDevicesOfUser(@NotNull UserRequest request) {
        Optional<User> optionalUser = userRepository.findByLogin(request.getLogin());
        User user;

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            log.info("Получен пользователь \"" + user.getLogin() + "\" из базы данных");
        } else {
            log.error("Пользователь \"" + request.getLogin() + "\" не найден в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        UserResponse userResponse = buildUserResponse(user);
        return userResponse.getDevices();
    }

    /**
     * Этот метод обеспечивает гарантию повторных попыток при возникновении CustomException,
     * чтобы избежать сбоев в работе прогрммы и гарантировать обновление пользователя
     */
    @Retryable(value = CustomException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000))
    @Transactional(
        isolation = Isolation.SERIALIZABLE,
        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = CustomException.class
    )
    public UserResponse updateUser(UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(userRequest.getLogin());
        
        if (!optionalUser.isPresent()) {
            throw new CustomException("Пользователь не найден");
        }
    
        User user = optionalUser.get();
        if (userRequest.getLogin() != null) {
            user.setLogin(userRequest.getLogin());
        }
    
        if (userRequest.getPassword() != null) {
            user.setPassword(userRequest.getPassword());
        }

        if (userRequest.getTelegramToken() != null) {
            TelegramToken telegramToken = user.getTelegramToken();
            telegramToken.setToken(userRequest.getTelegramToken());
        }

        userRepository.save(user);
        return buildUserResponse(user);
    }
    
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = ResponseStatusException.class
    )
    public UserResponse fullUpdateUser(UserRequest userRequest) {
        Optional<User> optionalUser  = userRepository.findByLogin(userRequest.getLogin());
        
        if (!optionalUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        User user = optionalUser.get();
        if (userRequest.getLogin() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть логин");
        }
    
        if (userRequest.getPassword() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть пароль");
        }

        if (userRequest.getTelegramToken() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "У пользователя должен быть телеграм токен");
        }

        user.setPassword(userRequest.getPassword());
        TelegramToken telegramToken = user.getTelegramToken();
        telegramToken.setToken(userRequest.getTelegramToken());
        userRepository.save(user);

        return buildUserResponse(user);
    }

    private UserResponse buildUserResponse(@NotNull User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setLogin(user.getLogin());
        userResponse.setPassword(user.getPassword());
        userResponse.setTelegramToken(user.getTelegramToken().getToken());

        List<DeviceResponse> devicesResponse = new ArrayList<>();
        List<Device> devices = user.getDevices();

        if (devices != null) {
            for (Device device : devices) {
                devicesResponse.add(deviceService.buildDeviceResponse(device));
            }
        }
        userResponse.setDevices(devicesResponse);
        return userResponse;
    }

    private User buildUserRequest(@NotNull UserRequest request) {
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(request.getPassword());
        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setToken(request.getTelegramToken());
        telegramToken.setUser(user);
        user.setTelegramToken(telegramToken);
        return user;
    }

    private boolean checkUserExistence(@NotNull User user) {
        try {
            return userRepository.existsByLogin(user.getLogin());
        } catch (Exception e) {
            log.error("Ошибка при проверке существования пользователя: " + e.getMessage());
            return false;
        }
    }
}
