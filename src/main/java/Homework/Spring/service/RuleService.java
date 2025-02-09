package Homework.Spring.service;

public class RuleService {

    public String availableRules() {
        return "Правила:";
    }

    public String deviceRules() {
        return "Правила устройства:";
    }

    public String applyRule() {
        return "Правило успешно добавлено";
    }

    public String deleteDeviceRule() {
        return "Правило успешно удалено";
    }
    
}
