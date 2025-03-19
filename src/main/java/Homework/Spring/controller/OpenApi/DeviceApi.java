package Homework.Spring.controller.OpenApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.RuleResponse;

@Tag(name = "Device Management", description = "API для управления устройствами")
public interface DeviceApi {

    @Operation(summary = "Добавление устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное добавление устройства"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь не найден"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Устройство уже существует")
    })
    DeviceResponse addDevice(long userId, DeviceRequest deviceRequest);

    @Operation(summary = "Удаление устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "id", description = "ID устройства для удаления")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное удаление устройства"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Устройство или пользователь не найдены"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "409", description = "Устройство принадлежит не этому пользователю")
    })
    DeviceResponse deleteDevice(long userId, long id);

    @Operation(summary = "Получение правил устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "id", description = "ID устройства")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешный ответ с правилами устройства"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Устройство не найдено")
    })
    List<RuleResponse> getDeviceRules(long userId, long id);

    @Operation(summary = "Обновление устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "id", description = "ID устройства для обновления")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное обновление устройства"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Устройство или пользователь не найдены")
    })
    DeviceResponse updateDevice(long userId, long id, DeviceRequest deviceRequest);

    @Operation(summary = "Полное обновление устройства")
    @Parameter(name = "userId", description = "ID пользователя, которому принадлежит устройство")
    @Parameter(name = "id", description = "ID устройства для полного обновления")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное полное обновление устройства"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Устройство или пользователь не найдены")
    })
    DeviceResponse fullUpdateDevice(long userId, long id, DeviceRequest deviceRequest);
}