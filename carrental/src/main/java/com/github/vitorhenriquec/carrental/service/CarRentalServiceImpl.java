package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.factory.SearchFactory;
import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CarRentalServiceImpl implements CarRentalService {

    private final CarRepository carRepository;


    @Override
    public CarSaveResponse saveCard(CarSaveRequest carSaveRequest) {
        log.info("method={}; carSaveRequest={}", "saveCar", carSaveRequest);

        var car = new Car();
        car.setModel(carSaveRequest.getModel());
        car.setBrand(carSaveRequest.getBrand());

        final var carSaved = carRepository.save(car);

        return CarSaveResponse.builder()
                .id(carSaved.getId())
                .brand(carSaved.getBrand())
                .model(carSaved.getModel())
                .build();
    }

    @Override
    public void updateCar(Long carId, CarUpdateRequest carUpdateRequest) throws CarNotFoundException {
        log.info("method={}; carUpdateRequest={}; carId={}", "updateCar", carUpdateRequest, carId);
        var carFound = carRepository.findById(carId)
                .orElseThrow(CarNotFoundException::new);

        carFound.setBrand(carUpdateRequest.getBrand());
        carFound.setModel(carUpdateRequest.getModel());
        carFound.setAvailable(carUpdateRequest.getAvailable());

        carRepository.save(carFound);
    }

    @Override
    public void deleteCar(Long carId) throws CarNotFoundException {
        log.info("method={}; carId={}", "deleteCar", carId);
        var carFound = carRepository.findById(carId)
                .orElseThrow(CarNotFoundException::new);

        carRepository.delete(carFound);
    }
}
