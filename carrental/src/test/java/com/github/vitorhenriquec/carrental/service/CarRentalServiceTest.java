package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class CarRentalServiceTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private CarRentalServiceImpl carService;


    @Test
    @DisplayName("Should save a car")
    public void shoudlSaveCar() {
        final var carSaveRequest = new CarSaveRequest("brand", "model");

        when(carRepository.save(any())).thenReturn(new Car(
                1L,
                "brand",
                "model",
                Boolean.FALSE
        ));

        final var carSaveResponse = Assertions.assertDoesNotThrow(
                ()  -> {
                    return carService.saveCard(carSaveRequest);
                }
        );

        verify(carRepository, times(1)).save(any());

        Assertions.assertEquals(carSaveResponse.getBrand(), "brand");
        Assertions.assertEquals(carSaveResponse.getModel(), "model");
    }

    @Test
    @DisplayName("Should update an existing car")
    public void shoudlUpdateAnExistingCar() {
        final var carUpdateRequest = new CarUpdateRequest("brand1", "model1", Boolean.TRUE);

        when(carRepository.findById(1L)).thenReturn(Optional.of(new Car(
                1L,
                "brand",
                "model",
                Boolean.FALSE
        )));

        Assertions.assertDoesNotThrow(
                ()  -> {
                   carService.updateCar(1L, carUpdateRequest);
                }
        );

        verify(carRepository, times(1)).findById(any());
        verify(carRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should not update a non existing car")
    public void shoudlNotUpdateAnExistingCar() {
        final var carUpdateRequest = new CarUpdateRequest("brand1", "model1", Boolean.TRUE);

        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                CarNotFoundException.class,
                ()  -> {
                    carService.updateCar(1L, carUpdateRequest);
                }
        );

        verify(carRepository, times(1)).findById(any());
        verify(carRepository, times(0)).save(any());
    }

    @Test
    @DisplayName("Should delete an existing car")
    public void shoudlDeleteAnExistingCar() {

        when(carRepository.findById(1L)).thenReturn(Optional.of(new Car(
                1L,
                "brand",
                "model",
                Boolean.FALSE
        )));

        Assertions.assertDoesNotThrow(
                ()  -> {
                    carService.deleteCar(1L);
                }
        );

        verify(carRepository, times(1)).findById(any());
        verify(carRepository, times(1)).delete(any());
    }

    @Test
    @DisplayName("Should not delete a non existing car")
    public void shoudlNotDeleteCar() {

        when(carRepository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                CarNotFoundException.class,
                ()  -> {
                    carService.deleteCar(1L);
                }
        );

        verify(carRepository, times(1)).findById(any());
        verify(carRepository, times(0)).delete(any());
    }

}
