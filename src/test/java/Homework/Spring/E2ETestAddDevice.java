package Homework.Spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.TelegramToken;
import Homework.Spring.entity.User;
import Homework.Spring.repository.DevicesRepository;
import Homework.Spring.repository.UsersRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = Homework1Application.class)
public class E2ETestAddDevice {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UsersRepository userRepository;

    @Autowired
    private DevicesRepository deviceRepository;

    @BeforeEach
    public void CreateUserAndDevice() {

        User user = new User();
        user.setLogin("Andrey");
        user.setPassword("123");
        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setToken("55-44");
        user.setTelegramToken(telegramToken);
        userRepository.save(user);

        Device device = new Device();
        device.setDeviceName("new");
        device.setType("Temperature Sensor");
        device.setUuid("123e4567-e89b-12d3-a456-426655440000");
        device.setUser(user);
        deviceRepository.save(device);
    }

    @Test
    public void testGetUserDevices() {

        ResponseEntity<List<DeviceResponse>> deviceResponseEntity = restTemplate.exchange(
        "/user/1/devices",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<DeviceResponse>>() {}
        );

        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());

        List<DeviceResponse> deviceResponse = deviceResponseEntity.getBody();
        DeviceResponse device = deviceResponse.get(0);
        assertEquals("new", device.getDeviceName());
        assertEquals("Temperature Sensor", device.getType());
        assertEquals("123e4567-e89b-12d3-a456-426655440000", device.getUuid());
    }
}