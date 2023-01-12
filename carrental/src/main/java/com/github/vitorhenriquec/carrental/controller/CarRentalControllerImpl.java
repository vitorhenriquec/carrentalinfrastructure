package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import com.github.vitorhenriquec.carrental.service.CarRentalService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/car")
@AllArgsConstructor
@Log4j2
public class CarRentalControllerImpl implements CarRentalController {

    private final CarRentalService carRentalService;


    @Override
    @PostMapping(consumes="application/json", produces = "application/json")
    public ResponseEntity<CarSaveResponse> saveCar(
            @RequestBody CarSaveRequest carSaveRequest
    ) {
        log.info("method={};", "saveCar");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carRentalService.saveCard(carSaveRequest));
    }

    @Override
    @PutMapping(value = "/{carId}", consumes="application/json")
    public void updateCar(
            @PathVariable Long carId,
            @RequestBody CarUpdateRequest carUpdateRequest
    ) throws CarNotFoundException {
        carRentalService.updateCar(carId, carUpdateRequest);
    }

}
