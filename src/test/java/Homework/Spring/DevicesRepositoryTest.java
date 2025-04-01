package Homework.Spring;

import Homework.Spring.entity.Device;
import Homework.Spring.entity.TelegramToken;
import Homework.Spring.entity.User;
import Homework.Spring.repository.DevicesRepository;
import Homework.Spring.repository.UsersRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DevicesRepositoryTest extends DatabaseSuite {

    @Autowired
    private DevicesRepository devicesRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    @Test
    void findByUuid() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("pass");

        usersRepository.save(user);

        Device device = new Device();
        device.setUuid("test-uuid");
        device.setDeviceName("test-device");
        device.setType("type");
        device.setUser(user);
        devicesRepository.save(device);

        Optional<Device> foundDevice = devicesRepository.findByUuid("test-uuid");

        assertThat(foundDevice).isPresent();
        assertThat(foundDevice.get().getUuid()).isEqualTo("test-uuid");
    }

    @Test
    void findByDeviceNameAndUserId_shouldReturnDevice() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("pass");
        TelegramToken telegramToken = new TelegramToken();
        telegramToken.setToken("fff");
        user.setTelegramToken(telegramToken);
        usersRepository.save(user);

        Device device = new Device();
        device.setUuid("test-uuid");
        device.setDeviceName("test-device");
        device.setType("type");
        device.setUser(user);
        devicesRepository.save(device);

        Optional<Device> foundDevice = devicesRepository.findByDeviceNameAndUserId(
            "test-device", 
            user.getId()
        );

        assertThat(foundDevice).isPresent();
        assertThat(foundDevice.get().getDeviceName()).isEqualTo("test-device");
        assertThat(foundDevice.get().getUser().getId()).isEqualTo(user.getId());
    }
}