package com.github.vitorhenriquec.carrental.service;

import com.github.vitorhenriquec.carrental.model.Car;
import com.github.vitorhenriquec.carrental.repository.CarRepository;
import static java.util.Map.entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class SearchColumnServiceTest {

    @MockBean
    private CarRepository carRepository;

    @Autowired
    private SearchBrandServiceImpl searchBrandService;

    @Autowired
    private SearchModelServiceImpl searchModelService;

    private final String model = "corsa";

    private final String brand = "chevrolet";

    private List<Car> cars;

    private Page page;

    private  Map<String, SearchColumnService> mapColumnToSearchColumnServices;

    private Map<String, String> mapColumnToValue;

    @BeforeEach
    public void initializeProperties() {
        cars = List.of(
                new Car(1l, brand, model, true),
                new Car(2l, brand, model, true)
        );

        page = new PageImpl<>(cars);

        when(carRepository.findAllAvailableByBrand(brand, page.getPageable()))
                .thenReturn(
                        page
                );

        when(carRepository.findAllAvailableByModel(model, page.getPageable()))
                .thenReturn(
                        page
                );

        mapColumnToSearchColumnServices = Map.ofEntries(
                entry("brand", searchBrandService),
                entry("model", searchModelService)
        );

        mapColumnToValue = Map.ofEntries(
                entry("brand", brand),
                entry("model", model)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"brand", "model"})
    @DisplayName("Should find a page of available cars by column")
    public void shouldFindPageAvailableCarsByColumn(String column) {

        final var pageCars = Assertions.assertDoesNotThrow(
                ()  -> {
                    return mapColumnToSearchColumnServices.get(column).findAvailable(mapColumnToValue.get(column), page.getPageable());
                }
        );

        Assertions.assertEquals(pageCars.getTotalElements(), 2);
        Assertions.assertEquals(pageCars.getContent(), cars);
    }
}
