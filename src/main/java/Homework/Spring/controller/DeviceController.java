package Homework.Spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Homework.Spring.dto.DeviceRequest;
import Homework.Spring.service.DeviceService;

@RestController
@RequestMapping("/{login}/device")
public class DeviceController {
    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/information")
    public String deviceInformation(@PathVariable String login) {
        return deviceService.deviceInformation();
    }

    @PostMapping("/add")
    public String addDevices(@PathVariable String login, @RequestBody DeviceRequest deviceRequest) {
        return deviceService.addDevices();
    }

    @PostMapping("/delete")
    public String deleteDevice(@PathVariable String login, @RequestBody DeviceRequest deviceRequest) {
        return deviceService.deleteDevice();        
    }
}
