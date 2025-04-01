package Homework.Spring.controller.actuator;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Endpoint(id = "uuid")
public class CustomActuatorEndpoint {

    @ReadOperation
    public String customUuid() {
        return ("uuid: " + UUID.randomUUID().toString());
    }
}