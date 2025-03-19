package Homework.Spring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceRequest {
    private Long id;
    private Long userId;
    private String deviceName;
    private String type;
}
