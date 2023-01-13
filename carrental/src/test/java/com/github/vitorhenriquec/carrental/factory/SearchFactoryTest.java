package com.github.vitorhenriquec.carrental.factory;

import com.github.vitorhenriquec.carrental.service.SearchBrandServiceImpl;
import com.github.vitorhenriquec.carrental.service.SearchColumnService;
import com.github.vitorhenriquec.carrental.service.SearchModelServiceImpl;
import static java.util.Map.entry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class SearchFactoryTest {

    @Autowired
    private SearchFactory searchFactory;

    @ParameterizedTest
    @ValueSource(strings = {"brand", "model"})
    @DisplayName("Should return an implementation of SearchColumnService for each column")
    public void shoudlReturnSearchColumnServiceImpl(String column) {
        var mapColumnToSearchColumnServiceClassType = Map.ofEntries(
            entry("brand", SearchBrandServiceImpl.class),
            entry("model",SearchModelServiceImpl.class)

        );

        final var searchColumnService = Assertions.assertDoesNotThrow(
                ()  -> {
                    return searchFactory.getBeanByColumn(column);
                }
        );

        Assertions.assertEquals(searchColumnService.getClass(), mapColumnToSearchColumnServiceClassType.get(column));
    }

    @Test
    @DisplayName("Should throw an exception for unknown bean")
    public void shoudlThrowExceptionForUnknownBean() {

        Assertions.assertThrows(
                NoSuchBeanDefinitionException.class,
                ()  -> {
                    searchFactory.getBeanByColumn("teste");
                }
        );
    }

}
