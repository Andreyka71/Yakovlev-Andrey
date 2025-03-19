
package Homework.Spring.controller;

import Homework.Spring.controller.OpenApi.RuleApi;
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
@RequestMapping("/{userId}/{deviceId}/rule")
public class RulesController implements RuleApi {
    private final RuleService ruleService;
    private final CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("RuleControllerCircuitBreaker");

    @PostMapping("/apply")
    @Override
    public RuleResponse applyRule(@PathVariable long userId, @PathVariable long deviceId, @RequestBody RuleRequest ruleRequest) {
        return circuitBreaker.executeSupplier(() -> {
            ruleRequest.setDeviceId(deviceId);
            ruleRequest.setUserId(userId);
            return ruleService.applyRule(ruleRequest);
        });
    }

    @DeleteMapping("/delete/{id}")
    @Override
    public RuleResponse deleteDeviceRule(@PathVariable long userId, @PathVariable long deviceId, @PathVariable long id) {
        return circuitBreaker.executeSupplier(() -> {
            RuleRequest ruleRequest = new RuleRequest();
            ruleRequest.setId(id);
            ruleRequest.setDeviceId(deviceId);
            ruleRequest.setUserId(userId);
            return ruleService.deleteDeviceRule(ruleRequest);
        });
    }
}