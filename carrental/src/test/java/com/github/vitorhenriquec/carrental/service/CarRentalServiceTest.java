package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CarRentalServiceTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarRentalService carService;


    @Test
    @DisplayName("Should save a car")
    public void shoudlSaveCar() {
        final var carSaveRequest = new CarSaveRequest("brand", "model");

        final var car = Assertions.assertDoesNotThrow(
                ()  -> {
                    return carService.saveCard(carSaveRequest);
                }
        );

        verify(carRepository, times(1)).save(any());
    }
}
