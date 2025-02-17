package Homework.homework1;

import Homework.Spring.Homework1Application;
import Homework.Spring.controller.DevicesController;
import Homework.Spring.dto.request.DeviceRequest;
import Homework.Spring.dto.response.DeviceResponse;
import Homework.Spring.service.DeviceService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DevicesController.class)
@ContextConfiguration(classes = Homework1Application.class)
public class DevicesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    public void testAddDeviceSuccess() throws Exception {
        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceName("123");
        deviceRequest.setType("123");

        when(deviceService.addDevice(any(DeviceRequest.class))).thenReturn(new DeviceResponse());

        mockMvc.perform(post("/1/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deviceRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateDeviceSuccess() throws Exception {
        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceName("123");
        deviceRequest.setType("123");

        when(deviceService.updateDevice(any(DeviceRequest.class))).thenReturn(new DeviceResponse());

        mockMvc.perform(put("/1/device/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deviceRequest)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddDeviceFailure() throws Exception {
        DeviceRequest deviceRequest = new DeviceRequest();
        deviceRequest.setDeviceName("123");
        deviceRequest.setType("123");

        when(deviceService.addDevice(any(DeviceRequest.class))).thenThrow(new RuntimeException("Пользователь не найден"));

        mockMvc.perform(post("/1/device/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(deviceRequest)))
                .andExpect(status().isInternalServerError());
    }
}

