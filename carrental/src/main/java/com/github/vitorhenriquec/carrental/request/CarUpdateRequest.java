package com.github.vitorhenriquec.carrental.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CarUpdateRequest {

    private String brand;

    private String model;

    private Boolean available;

}
