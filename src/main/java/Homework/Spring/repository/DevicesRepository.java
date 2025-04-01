package Homework.Spring.repository;

import Homework.Spring.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DevicesRepository extends JpaRepository<Device, Integer> {

    Optional<Device> findByUuid(String uuid);
    Optional<Device> findByDeviceNameAndUserId(String deviceName, Integer userId);
}