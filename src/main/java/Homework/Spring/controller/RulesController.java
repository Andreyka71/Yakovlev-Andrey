
package Homework.Spring.controller;

import Homework.Spring.dto.request.RuleRequest;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.service.RuleService;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/{login}/{deviceName}/rule")
public class RulesController {
    private final RuleService ruleService;
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("RuleControllerCircuitBreaker");

    @PostMapping("/apply")
    public RuleResponse applyRule(@PathVariable String login, @PathVariable String deviceName, @RequestBody RuleRequest ruleRequest) {
        return circuitBreaker.executeSupplier(() -> {
            ruleRequest.setDeviceName(deviceName);
            ruleRequest.setLogin(login);
            return ruleService.applyRule(ruleRequest);
        });
    }

    @DeleteMapping("/delete")
    public RuleResponse deleteDeviceRule(@PathVariable String login, @PathVariable String deviceName, @RequestBody RuleRequest ruleRequest) {
        return circuitBreaker.executeSupplier(() -> {
            ruleRequest.setDeviceName(deviceName);
            ruleRequest.setLogin(login);
            return ruleService.deleteDeviceRule(ruleRequest);
        });
    }
}