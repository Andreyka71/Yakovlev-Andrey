package Homework.Spring.service;

import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.Rule;
import Homework.Spring.entity.User;
import Homework.Spring.repository.DevicesRepository;
import Homework.Spring.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class DeviceService {

    private final DevicesRepository deviceRepository;
    private final UsersRepository userRepository;
    private final RuleService ruleService;
        
    public DeviceResponse addDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findById(deviceRequest.getUserId());
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }
        Device device = buildDeviceRequest(deviceRequest, user);
        device = deviceRepository.addDevice(device);
        user.getDevices().add(device);
        DeviceResponse deviceResponse = buildDeviceResponse(device);
        return deviceResponse;
    }

    public DeviceResponse deleteDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findById(deviceRequest.getUserId());
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }
        Optional<Device> optionalDevice = deviceRepository.findById(deviceRequest.getId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким названием не найдено");
        }
        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }
        user.getDevices().remove(device);
        DeviceResponse deviceResponse = buildDeviceResponse(device);
        deviceRepository.deleteDevice(device.getId());
        return deviceResponse;
    }

    public List<RuleResponse> getDeviceRules(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findById(deviceRequest.getUserId());
        User user = new User();
        
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findById(deviceRequest.getId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        DeviceResponse deviceResponse = buildDeviceResponse(device);
        return deviceResponse.getRules();
    }

    public DeviceResponse updateDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findById(deviceRequest.getUserId());
        User user = new User();
        
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findById(deviceRequest.getId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        if (deviceRequest.getDeviceName() != null) {
            device.setDeviceName(deviceRequest.getDeviceName());
        }

        if (deviceRequest.getType() != null) {
            device.setType(deviceRequest.getType());
        }
        return buildDeviceResponse(device);
    }

    public DeviceResponse fullUpdateDevice(DeviceRequest deviceRequest) {
        Optional<User> optionalUser = userRepository.findById(deviceRequest.getUserId());
        User user = new User();
        
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findById(deviceRequest.getId());
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
        device.setDeviceName(deviceRequest.getDeviceName());
        device.setType(deviceRequest.getType());
        return buildDeviceResponse(device);
    }

    public DeviceResponse buildDeviceResponse(Device device) {
        DeviceResponse deviceResponse = new DeviceResponse();
        deviceResponse.setId(device.getId());
        deviceResponse.setDeviceName(device.getDeviceName());
        deviceResponse.setType(device.getType());
        deviceResponse.setUserId(device.getUser().getId());

        List<RuleResponse> rulesResponse = new ArrayList<>();
        List<Rule> rules = device.getRules();
        for (Rule rule : rules) {
            rulesResponse.add(ruleService.buildRuleResponse(rule));
        }
        deviceResponse.setRules(rulesResponse);

        return deviceResponse;
    }

    private Device buildDeviceRequest(DeviceRequest request, User user) {
        Device device = new Device();
        device.setId(request.getId());
        device.setDeviceName(request.getDeviceName());
        device.setType(request.getType());
        device.setUser(user);
        return device;
    }

}
