package com.github.vitorhenriquec.carrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitorhenriquec.carrental.CarrentalApplication;
import com.github.vitorhenriquec.carrental.config.ObjectMapperConfig;
import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.model.User;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {CarrentalApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(value = {ObjectMapperConfig.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SqlGroup(value = {
        @Sql(value = "classpath:scripts/delete_car_record.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    }
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CarRentalControllerTest {

    @Autowired
    private WebApplicationContext applicationContext;

    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String path = "/v1/car";

    @BeforeAll
    public void init(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .build();

        setupUser();
    }
    private void setupUser(){
        User user = new User();
        user.setPassword("1234567");
        user.setEmail("test@gmail.com");
        userRepository.save(user);
    }

    @Test
    @DisplayName("Should save a car successfully")
    @WithUserDetails("test@gmail.com")
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

    @Test
    @DisplayName("Should update an existing car successfully")
    @WithUserDetails("test@gmail.com")
    public void shouldUpdateCarSucessufully() throws Exception {
        final var carUpdateRequest = new CarUpdateRequest("brand1", "model1", true);

        final var carSalved = carRepository.save(new Car(1L, "brand", "model", false));

        mockMvc.perform(
                        put(path + "/"+ carSalved.getId().toString())
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(carUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should not update a non existing car")
    @WithUserDetails("test@gmail.com")
    public void shouldNotUpdateCar() throws Exception {
        final var carUpdateRequest = new CarUpdateRequest("brand1", "model1", true);

        mockMvc.perform(
                        put(path + "/1")
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(carUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isNotFound());

    }


    @Test
    @DisplayName("Should delete an existing car successfully")
    @WithUserDetails("test@gmail.com")
    public void shouldDeleteCarSucessufully() throws Exception {
        final var carUpdateRequest = new CarUpdateRequest("brand1", "model1", true);

        final var carSalved = carRepository.save(new Car(1L, "brand", "model", false));

        mockMvc.perform(
                        delete(path + "/" + carSalved.getId().toString())
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(carUpdateRequest))
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName("Should not delete a non existing car")
    @WithUserDetails("test@gmail.com")
    public void shouldNotDeleteCar() throws Exception {
        mockMvc.perform(
                        delete(path + "/1")
                )
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @ParameterizedTest
    @ValueSource(strings = {"brand", "model"})
    @DisplayName("Should find available cars by column")
    @WithUserDetails("test@gmail.com")
    public void shouldFindAvailableCarsByColumn(String column) throws Exception {
        final var parameters = "?column="+ column +"&value=test";

        mockMvc.perform(
                        get(path + parameters)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }

}
