package Homework.Spring.service;

import Homework.Spring.dto.request.RuleRequest;
import Homework.Spring.dto.response.RuleResponse;
import Homework.Spring.entity.Device;
import Homework.Spring.entity.Rule;
import Homework.Spring.entity.User;
import Homework.Spring.repository.DevicesRepository;
import Homework.Spring.repository.RulesRepository;
import Homework.Spring.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class RuleService {

    private final UsersRepository userRepository;
    private final DevicesRepository deviceRepository;
    private final RulesRepository ruleRepository;

    public RuleResponse applyRule(RuleRequest ruleRequest) {
        Optional<User> optionalUser = userRepository.findById(ruleRequest.getUserId());
        User user = new User();
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь с таким id не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findById(ruleRequest.getDeviceId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        List<Rule> rulesOfDevice = device.getRules();
        for (Rule rule : rulesOfDevice) {
            if (ruleRequest.getRule().equals(rule.getRule())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT,"Правило с таким название уже есть у этого устройства");
            }
        }

        String pattern = "^Temperature/[-+]?\\d+(\\.\\d+)?/[-+]?\\d+(\\.\\d+)?$";
        if (!Pattern.matches(pattern, ruleRequest.getRule())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Неверный формат правила");
        }

        Rule rule = buildRuleRequest(ruleRequest, device);
        RuleResponse ruleResponse = buildRuleResponse(ruleRepository.addRule(rule));
        rule.setId(ruleResponse.getId());
        device.getRules().add(rule);
        return ruleResponse;
    }

    public RuleResponse deleteDeviceRule(RuleRequest ruleRequest) {
        Optional<User> optionalUser = userRepository.findById(ruleRequest.getUserId());
        User user = new User();

        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь с таким id не найден");
        }

        Optional<Device> optionalDevice = deviceRepository.findById(ruleRequest.getDeviceId());
        if (!optionalDevice.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Устройство с таким id не найдено");
        }

        Device device = optionalDevice.get();
        if (!user.equals(device.getUser())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Устройство принадлежит не этому пользователю");
        }

        Optional<Rule> optionalRule = ruleRepository.findById(ruleRequest.getId());
        if (!optionalRule.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Правило с таким id не найдено");
        }
        
        Rule rule = optionalRule.get();
        RuleResponse ruleResponse = buildRuleResponse(rule);
        ruleRepository.deleteRule(rule.getId());
        device.getRules().remove(rule);
        return ruleResponse;
    }

    public RuleResponse buildRuleResponse(Rule rule) {
        RuleResponse ruleResponse = new RuleResponse();
        ruleResponse.setId(rule.getId());
        ruleResponse.setRule(rule.getRule());
        ruleResponse.setDeviceId(rule.getDevice().getId());
        return ruleResponse;
    }

    private Rule buildRuleRequest(RuleRequest request, Device device) {
        Rule rule = new Rule();
        rule.setId(request.getId());
        rule.setRule(request.getRule());
        rule.setDevice(device);
        return rule;
    }
}
