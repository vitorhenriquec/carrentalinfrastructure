package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;

public interface CarRentalService {

    CarSaveResponse saveCard(CarSaveRequest carSaveRequest);
}
