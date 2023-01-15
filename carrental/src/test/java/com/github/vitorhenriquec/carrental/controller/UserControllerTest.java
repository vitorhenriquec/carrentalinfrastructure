package com.github.vitorhenriquec.carrental.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vitorhenriquec.carrental.CarrentalApplication;
import com.github.vitorhenriquec.carrental.config.ObjectMapperConfig;
import com.github.vitorhenriquec.carrental.model.User;
import com.github.vitorhenriquec.carrental.repository.UserRepository;
import com.github.vitorhenriquec.carrental.request.UserSignUpRequest;
import static org.hamcrest.CoreMatchers.equalTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
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
@AutoConfigureMockMvc
@SqlGroup(value = {
        @Sql(value = "classpath:scripts/delete_user_record.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    }
)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String path = "/v1/user";


    @Test
    @DisplayName("Should save a user successfully")
    public void shouldSaveCardSucessufully() throws Exception {
        final var userSignUpRequest = new UserSignUpRequest("email", "password");

        mockMvc.perform(
                post(path)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userSignUpRequest))
        )
                .andDo(print())
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("Should not save an already existing user")
    public void shouldNotSaveAnAlreadyExistingUser() throws Exception {
        final var userSignUpRequest = new UserSignUpRequest("email", "password");

        userRepository.save(new User(1L,"password", "email"));

        mockMvc.perform(
                        post(path)
                                .accept(MediaType.APPLICATION_JSON_VALUE)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(userSignUpRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
