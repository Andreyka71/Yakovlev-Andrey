package Homework.Spring.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleResponse {
    private String rule;
    private Integer lowestValue;
    private Integer highestValue;
    private String deviceName;
}
