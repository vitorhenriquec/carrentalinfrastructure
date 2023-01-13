package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("searchmodel")
@AllArgsConstructor
@Log4j2
public class SearchModelServiceImpl implements SearchColumnService {

    private final CarRepository carRepository;

    @Override
    public Page<Car> findAvailable(String value, Pageable pageable) {
        log.info("method={};", "findAvailable");
        return carRepository.findAllAvailableByModel(value, pageable);
    }
}
