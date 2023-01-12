package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class CarRentalServiceImpl implements CarRentalService {

    private final CarRepository carRepository;


    @Override
    public CarSaveResponse saveCard(CarSaveRequest carSaveRequest) {
        log.info("method={}, carSaveRequest={}", "saveCar", carSaveRequest);

        final var car = Car.builder().model(
                carSaveRequest.getModel()).brand(carSaveRequest.getBrand()
        ).build();

        final var carSaved = carRepository.save(car);

        return CarSaveResponse.builder().id(carSaved.getId()).build();
    }
}
