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

import Homework.Spring.controller.OpenApi.DeviceApi;
import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.service.DeviceService;
import io.github.resilience4j.ratelimiter.RateLimiter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/{userId}/device")
public class DevicesController implements DeviceApi {
    private final DeviceService deviceService;
    private final RateLimiter rateLimiter = RateLimiter.ofDefaults("DeviceControllerRateLimiter");

    @PostMapping("/add")
    @Override
    public DeviceResponse addDevice(@PathVariable long userId, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setUserId(userId);
            return deviceService.addDevice(deviceRequest);
        });
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public DeviceResponse deleteDevice(@PathVariable long userId, @PathVariable long id) {
        return rateLimiter.executeSupplier(() -> {
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setId(id);
            deviceRequest.setUserId(userId);
            return deviceService.deleteDevice(deviceRequest);
        });
    }

    @GetMapping("/rules/{id}")
    @Override
    public List<RuleResponse> getDeviceRules(@PathVariable long userId, @PathVariable long id) {
        return rateLimiter.executeSupplier(() -> {
            DeviceRequest deviceRequest = new DeviceRequest();
            deviceRequest.setId(id);
            deviceRequest.setUserId(userId);
            return deviceService.getDeviceRules(deviceRequest);
        });
    }

    @PutMapping("/update/{id}")
    @Override
    public DeviceResponse updateDevice(@PathVariable long userId, @PathVariable long id, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setId(id);
            deviceRequest.setUserId(userId);
            return deviceService.updateDevice(deviceRequest);
        });
    }

    @PatchMapping("/fullUpdate/{id}")
    @Override
    public DeviceResponse fullUpdateDevice(@PathVariable long userId, @PathVariable long id, @RequestBody DeviceRequest deviceRequest) {
        return rateLimiter.executeSupplier(() -> {
            deviceRequest.setId(id);
            deviceRequest.setUserId(userId);
            return deviceService.fullUpdateDevice(deviceRequest);
        });
    }
}
