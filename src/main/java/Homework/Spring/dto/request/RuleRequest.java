package Homework.Spring.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RuleRequest {
    private Long id;
    private Long userId;
    private Long deviceId;
    private String rule;
}