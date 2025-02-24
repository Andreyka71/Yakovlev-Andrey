package Homework.Spring.controller.OpenApi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import Homework.Spring.dto.request.UserRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.dto.response.UserResponse;

@Tag(name = "User  Management", description = "API для управления пользователями")
public interface UserApi {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешная регистрация пользователя"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Некорректные данные для регистрации")
    })
    UserResponse registration(UserRequest userRequest);

    @Operation(summary = "Получение списка устройств пользователя")
    @Parameter(name = "id", description = "ID пользователя")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешный ответ с списком устройств"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    List<DeviceResponse> listOfDevicesOfUser (long id);

    @Operation(summary = "Обновление информации о пользователе")
    @Parameter(name = "id", description = "ID пользователя")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное обновление пользователя"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    UserResponse updateRule(long id, UserRequest userRequest);

    @Operation(summary = "Полное обновление информации о пользователе")
    @Parameter(name = "id", description = "ID пользователя")
    @ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Успешное полное обновление пользователя"),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    UserResponse fullUpdateRule(long id, UserRequest userRequest);
}