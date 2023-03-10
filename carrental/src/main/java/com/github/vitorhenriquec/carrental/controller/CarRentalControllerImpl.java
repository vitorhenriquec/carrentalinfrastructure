package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.factory.SearchFactory;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import com.github.vitorhenriquec.carrental.service.CarRentalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/car")
@RequiredArgsConstructor
@Log4j2
public class CarRentalControllerImpl implements CarRentalController {

    private final CarRentalService carRentalService;

    private final SearchFactory searchFactory;


    @Override
    @PostMapping(consumes="application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CarSaveResponse> saveCar(
            @RequestBody CarSaveRequest carSaveRequest
    ) {
        log.info("method={};", "saveCar");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(carRentalService.saveCar(carSaveRequest));
    }

    @Override
    @PutMapping(value = "/{carId}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public void updateCar(
            @PathVariable Long carId,
            @RequestBody CarUpdateRequest carUpdateRequest
    ) throws CarNotFoundException {
        log.info("method={};", "updateCar");
        carRentalService.updateCar(carId, carUpdateRequest);
    }

    @Override
    @DeleteMapping(value = "/{carId}")
    public void deleteCar(
            @PathVariable Long carId
    ) throws CarNotFoundException {
        log.info("method={};", "deleteCar");
        carRentalService.deleteCar(carId);
    }

    @Override
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> findAvailableBy(
            @RequestParam String column,
            @RequestParam String value,
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        log.info("method={};", "findAvailableBy");
        return ResponseEntity.ok(
                searchFactory.getBeanByColumn(column).findAvailable(value, pageable)
        );
    }

}
