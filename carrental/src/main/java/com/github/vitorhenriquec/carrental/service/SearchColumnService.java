package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchColumnService {

    Page<Car> findAvailable(String value, Pageable pageable);

}
