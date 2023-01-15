package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.exception.CarNotFoundException;
import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.request.CarUpdateRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name= "Car Rental",
        description= "Car rental service"
)
public interface CarRentalController {

    @Operation(description = "Save a new car")
    ResponseEntity<CarSaveResponse> saveCar(
            @Parameter(name="body", required = true, description = "Car data to be saved") CarSaveRequest carSaveRequest,
            @Parameter(in = ParameterIn.HEADER, name="Authorization") String authorization
    );

    @Operation(description = "Update a car")
    void updateCar(
            @Parameter(in = ParameterIn.PATH, description = "Car id") Long carId,
            @Parameter(name="body", required = true, description = "Car data to be updated") CarUpdateRequest carUpdateRequest,
            @Parameter(in = ParameterIn.HEADER, name="Authorization") String authorization
    ) throws CarNotFoundException;

    @Operation(description = "Delete a car")
    void deleteCar(
            @Parameter(in = ParameterIn.PATH, description = "Car id") Long carId,
            @Parameter(in = ParameterIn.HEADER, name="Authorization") String authorization
    ) throws CarNotFoundException;
}
