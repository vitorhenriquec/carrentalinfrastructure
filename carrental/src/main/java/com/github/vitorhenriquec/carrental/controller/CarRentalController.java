package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(
        name= "Car Rental",
        description= "Car rental service"
)
public interface CarRentalController {

    @Operation(description = "Save a new car")
    ResponseEntity<CarSaveResponse> saveCar(
            @Parameter(name="body", required = true, description = "Car data to be saved") CarSaveRequest carSaveRequest
    );

    @Operation(description = "Update a car")
    void updateCar(
            @Parameter(in = ParameterIn.PATH, description = "Car id") Long carId,
            @Parameter(name="body", required = true, description = "Car data to be updated") CarUpdateRequest carUpdateRequest
    ) throws CarNotFoundException;

    @Operation(description = "Delete a car")
    void deleteCar(
            @Parameter(in = ParameterIn.PATH, description = "Car id") Long carId
    ) throws CarNotFoundException;

    @Operation(description = "Find car by a specific column and its value")
    ResponseEntity<?> findAvailableBy(
            @Parameter(in = ParameterIn.HEADER, description = "Car column for the search") String column,
            @Parameter(in = ParameterIn.HEADER, description = "Car column's value") String value,
            Pageable pageable
    );
}
