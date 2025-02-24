package Homework.Spring.repository;

import org.springframework.stereotype.Repository;

import Homework.Spring.entity.Device;
import Homework.Spring.entity.Rule;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DevicesRepository {

    private final Map<Long, Device> devices = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public Device addDevice(Device device) {
        Long id = idCounter.incrementAndGet();
        device.setId(id);
        device.setRules(new ArrayList<Rule>());
        devices.put(id, device);
        // логирование
        return device;
    }

    public Optional<Device> findById(Long id) {
        return Optional.ofNullable(devices.get(id));
    }

    public void deleteDevice(Long id) {
        devices.remove(id);
        // логирование
    }

    public boolean existsById(Long id) {
        return devices.containsKey(id);
    }
}

