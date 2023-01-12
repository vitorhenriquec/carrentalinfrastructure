package com.github.vitorhenriquec.carrental.repository;

import com.github.vitorhenriquec.carrental.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
