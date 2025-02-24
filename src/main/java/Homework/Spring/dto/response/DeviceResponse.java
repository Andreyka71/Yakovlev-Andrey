package Homework.Spring.dto.response;

import java.util.List;

import lombok.Data;

@Data
public class DeviceResponse {
    private Long id;
    private Long userId;
    private String type;
    private String deviceName;
    private List<RuleResponse> rules;
}
