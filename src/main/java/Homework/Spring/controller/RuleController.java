package Homework.Spring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Homework.Spring.dto.RuleRequest;
import Homework.Spring.service.RuleService;

@RestController
@RequestMapping("/rule")
public class RuleController {
    private static RuleService rule = new RuleService();

    @GetMapping("/availableRules")
    public String availableDeviceRules() {
       return rule.availableRules(); 
    }

    @GetMapping("/deviceRules")
    public String deviceRules() {
        return rule.deviceRules();
    }

    @PostMapping("/apply")
    public String applyRule(@RequestBody RuleRequest ruleRequest) {
        return rule.applyRule();
    }

    @PostMapping("/delete")
    public String deleteDeviceRule(@RequestBody RuleRequest ruleRequest) {
        return rule.deleteDeviceRule();
    }
}