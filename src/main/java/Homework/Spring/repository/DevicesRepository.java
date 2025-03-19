package Homework.Spring.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.reactive.function.client.WebClient;

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

    private final RestTemplate restTemplate = new RestTemplate();
    private final WebClient webClient = WebClient.create();

    public String fetchRandomData() {
        String url = "http://kva1/api/data";
        return restTemplate.getForObject(url, String.class);
    }

    public String fetchDataWithWebClient() {
        String url = "http://kva2/api/data";
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

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

