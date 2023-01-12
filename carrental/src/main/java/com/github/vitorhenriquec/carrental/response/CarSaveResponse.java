package com.github.vitorhenriquec.carrental.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class CarSaveResponse {
    private Long id;

    private String brand;

    private String model;
}
