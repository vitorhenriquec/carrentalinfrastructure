package com.github.vitorhenriquec.carrental.repository;

import com.github.vitorhenriquec.carrental.model.Car;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(
            "SELECT distinct c FROM Car c WHERE c.available IS true and c.brand=:brand"
    )
    Page<Car> findAllAvailableByBrand(
            @Param("brand") String brand,
            Pageable pageable
    );


    @Query(
            "SELECT distinct c FROM Car c WHERE c.available IS true and c.brand=:brand"
    )
    Page<Car> findAllAvailableByModel(
            @Param("brand") String brand,
            Pageable pageable
    );
}
