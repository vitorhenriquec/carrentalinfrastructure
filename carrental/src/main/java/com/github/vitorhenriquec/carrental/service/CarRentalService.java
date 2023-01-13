package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarRentalService {

    CarSaveResponse saveCard(CarSaveRequest carSaveRequest);

    void updateCar(Long carId, CarUpdateRequest carUpdateRequest) throws CarNotFoundException;

    void deleteCar(Long carId) throws CarNotFoundException;
}
