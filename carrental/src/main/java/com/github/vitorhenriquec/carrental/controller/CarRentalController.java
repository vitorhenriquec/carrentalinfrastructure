package com.github.vitorhenriquec.carrental.controller;

import com.github.vitorhenriquec.carrental.request.CarSaveRequest;
import com.github.vitorhenriquec.carrental.response.CarSaveResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(
        name= "Car Rental",
        description= "Car rental service"
)
public interface CarRentalController {

    @Operation(description = "Save a new car")
    ResponseEntity<CarSaveResponse> saveCar(
            @Parameter(name="body", required = true, description = "Car data to be saved") CarSaveRequest carSaveRequest
    );

}
