package Homework.Spring.service;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.Rule;
import Homework.Spring.entity.User;
import Homework.Spring.repository.DevicesRepository;
import Homework.Spring.repository.UsersRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DeviceService {

    private final DevicesRepository deviceRepository;
    private final UsersRepository userRepository;
    private final RuleService ruleService;

    /**
     * Этот метод обеспечивает выполнение операции добавления устройства ровно один раз
     * Используется synchronized, чтобы гарантировать, что два потока не могут одновременно
     * добавить одного и тоже устройство. Если устройство уже существует, метод не будет
     * добавлять его повторно.
     */
    @CachePut(value = "devices", key = "#deviceRequest.id")
    @Transactional(
        isolation = Isolation.SERIALIZABLE,
        propagation = Propagation.REQUIRED,
        rollbackFor = ResponseStatusException.class
    )
    public DeviceResponse addDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(deviceRequest.getLogin());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            log.info("Получен пользователь \"" + user.getLogin() + "\" из базы данных");
        } else {
            log.error("Пользователь \"" + deviceRequest.getLogin() + "\" не найден в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndUserId(deviceRequest.getDeviceName(), user.getId());
        if (optionalDevice.isPresent()) {
            log.error("Устройство \"" + deviceRequest.getDeviceName() + "\" не найдено в базе данных");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Устройство с таким названием уже существует");
        }

        log.info("Получено устройство \"" + deviceRequest.getDeviceName() + "\" из базы данных");

        Device device = buildDeviceRequest(deviceRequest, user);
        deviceRepository.save(device);
        return buildDeviceResponse(device);
    }

    @CacheEvict(value = "devices", key = "#deviceRequest.id")
    @Transactional(
        propagation = Propagation.REQUIRED,
        rollbackFor = ResponseStatusException.class
    )
    public DeviceResponse deleteDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(deviceRequest.getLogin());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            log.info("Получен пользователь \"" + user.getLogin() + "\" из базы данных");
        } else {
            log.error("Пользователь \"" + deviceRequest.getLogin() + "\" не найден в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndUserId(deviceRequest.getDeviceName(), user.getId());
        if (optionalDevice.isEmpty()) {
            log.error("Устройство \"" + deviceRequest.getDeviceName() + "\" не найдено в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Устройство с таким названием не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            log.error("У пользователя \"" + deviceRequest.getLogin() + "\" отсутсвует устройство \"" + deviceRequest.getUuid() + "\"");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Устройство принадлежит не этому пользователю");
        }

        DeviceResponse deviceResponse = buildDeviceResponse(device);
        deviceRepository.delete(device);
        return deviceResponse;
    }

    @CachePut(value = "devices", key = "#deviceRequest.id")
    @Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRED,
        rollbackFor = ResponseStatusException.class
    )
    public DeviceResponse updateDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(deviceRequest.getLogin());
        User user = new User();
        
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndUserId(deviceRequest.getDeviceName(), user.getId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        if (deviceRequest.getUuid() != null) {
            device.setUuid(deviceRequest.getUuid());
        }

        if (deviceRequest.getType() != null) {
            device.setType(deviceRequest.getType());
        }
        deviceRepository.save(device);
        return buildDeviceResponse(device);
    }

    @Cacheable(value = "devices", key = "#deviceRequest.id")
    @Transactional(
    isolation = Isolation.READ_COMMITTED,
    propagation = Propagation.REQUIRED,
    rollbackFor = ResponseStatusException.class
    )
    public DeviceResponse fullUpdateDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(deviceRequest.getLogin());
        User user = new User();
        
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndUserId(deviceRequest.getDeviceName(), user.getId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        if (deviceRequest.getDeviceName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"У устройства должно быть название");
        }

        if (deviceRequest.getType() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"У устройства должен быть тип");
        }
        device.setUuid(deviceRequest.getUuid());
        device.setType(deviceRequest.getType());
        deviceRepository.save(device);
        return buildDeviceResponse(device);
    }

    @Cacheable(value = "devices", key = "#deviceRequest.id")
    @Transactional(readOnly = true)
    public List<RuleResponse> getDeviceRules(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findByLogin(deviceRequest.getLogin());
        User user;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
            log.info("Получен пользователь \"" + user.getLogin() + "\" из базы данных");
        } else {
            log.error("Пользователь \"" + deviceRequest.getLogin() + "\" не найден в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findByDeviceNameAndUserId(deviceRequest.getDeviceName(), user.getId());
        if (optionalDevice.isEmpty()) {
            log.error("Устройство \"" + deviceRequest.getDeviceName() + "\" не найдено в базе данных");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Устройство с таким названием не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            log.error("У пользователя \"" + deviceRequest.getLogin() + "\" отсутсвует устройство \"" + deviceRequest.getDeviceName() + "\"");
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Устройство принадлежит не этому пользователю");
        }

        DeviceResponse deviceResponse = buildDeviceResponse(device);
        return deviceResponse.getRules();
    }

    public DeviceResponse buildDeviceResponse(@NotNull Device device) {
        DeviceResponse deviceResponse = new DeviceResponse();
        deviceResponse.setUuid(device.getUuid());
        deviceResponse.setType(device.getType());
        deviceResponse.setDeviceName(device.getDeviceName());
        deviceResponse.setLogin(device.getUser().getLogin());

        List<RuleResponse> rulesResponse = new ArrayList<>();
        List<Rule> rules = device.getRules();

        if (rules != null) {
            for (Rule rule : rules) {
                rulesResponse.add(ruleService.buildRuleResponse(rule));
            }
        }
        deviceResponse.setRules(rulesResponse);

        return deviceResponse;
    }

    private Device buildDeviceRequest(@NotNull DeviceRequest request, User user) {
        Device device = new Device();
        device.setUuid(request.getUuid());
        device.setType(request.getType());
        device.setDeviceName(request.getDeviceName());
        device.setUser(user);
        return device;
    }
}
