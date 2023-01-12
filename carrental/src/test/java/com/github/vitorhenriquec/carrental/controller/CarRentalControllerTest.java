package com.github.vitorhenriquec.carrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitorhenriquec.carrental.CarrentalApplication;
import com.github.vitorhenriquec.carrental.configuration.ObjectMapperConfig;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CarrentalApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {ObjectMapperConfig.class})
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarRentalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String path = "/v1/car";

    @Test
    @DisplayName("Should save a car successfully")
    public void shouldSaveCardSucessufully() throws Exception {
        final var carSaveRequest = new CarSaveRequest("brand", "model");

        mockMvc.perform(
                post(path)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(carSaveRequest))
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(
                        jsonPath("brand", equalTo(carSaveRequest.getBrand()))
                ).andExpect(
                        jsonPath("model", equalTo(carSaveRequest.getModel()))
                );

    }

}
