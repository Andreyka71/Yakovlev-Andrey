package Homework.Spring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.service.DeviceService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{login}/device")
public class DevicesController {
    private final DeviceService deviceService;
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("DeviceControllerRateLimiter");

    @PostMapping("/add")
    public DeviceResponse addDevice(@PathVariable String login, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setLogin(login);
            return deviceService.addDevice(deviceRequest);
        });
    }

    @DeleteMapping("/delete/{deviceName}")
    public DeviceResponse deleteDevice(@PathVariable String login, @PathVariable String deviceName) {
        return rateLimiter.executeSupplier(() -> {
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setLogin(login);
            deviceRequest.setDeviceName(deviceName);
            return deviceService.deleteDevice(deviceRequest);
        });
    }

    @GetMapping("/rules/{deviceName}")
    public List<RuleResponse> getDeviceRules(@PathVariable String login, @PathVariable String deviceName) {
        return rateLimiter.executeSupplier(() -> {
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setLogin(login);
            deviceRequest.setDeviceName(deviceName);
            return deviceService.getDeviceRules(deviceRequest);
        });
    }

    @PutMapping("/update/{deviceName}")
    public DeviceResponse updateDevice(@PathVariable String login, @PathVariable String deviceName, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setLogin(login);
            deviceRequest.setDeviceName(deviceName);
            return deviceService.updateDevice(deviceRequest);
        });
    }

    @PatchMapping("/fullUpdate/{deviceName}")
    public DeviceResponse fullUpdateDevice(@PathVariable String login, @PathVariable String deviceName, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setLogin(login);
            deviceRequest.setDeviceName(deviceName);
            return deviceService.fullUpdateDevice(deviceRequest);
        });
    }
}
