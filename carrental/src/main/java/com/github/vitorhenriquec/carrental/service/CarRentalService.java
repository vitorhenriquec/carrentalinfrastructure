package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;

public interface CarRentalService {

    CarSaveResponse saveCar(CarSaveRequest carSaveRequest);

    void updateCar(Long carId, CarUpdateRequest carUpdateRequest) throws CarNotFoundException;

    void deleteCar(Long carId) throws CarNotFoundException;
}
