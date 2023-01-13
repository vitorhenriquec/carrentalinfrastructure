package com.github.vitorhenriquec.carrental.factory;

import com.github.vitorhenriquec.carrental.service.SearchColumnService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class SearchFactory {

    private final BeanFactory beanFactory;

    public SearchColumnService getBeanByColumn(String column){
        log.info("method={}; column={};", "getBeanByColumn", column);

        return (SearchColumnService) beanFactory.getBean("search" + column);
    }
}
