package Homework.Spring.controller.OpenApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import Homework.Spring.dto.request.RuleRequest;
import Homework.Spring.dto.response.RuleResponse;

@Tag(name = "Rule Management", description = "API для управления правилами устройств")
public interface RuleApi {

    @Operation(summary = "Применение правила к устройству")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "deviceId", description = "ID устройства, к которому применяется правило")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Правило успешно применено"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь или устройство не найдены"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    })
    RuleResponse applyRule(long userId, long deviceId, RuleRequest ruleRequest);

    @Operation(summary = "Удаление правила устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "deviceId", description = "ID устройства, к которому относится правило")
    @Parameter(name = "id", description = "ID правила для удаления")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Правило успешно удалено"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Правило, пользователь или устройство не найдены"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Ошибка в запросе")
    })
    RuleResponse deleteDeviceRule(long userId, long deviceId, long id);
}

