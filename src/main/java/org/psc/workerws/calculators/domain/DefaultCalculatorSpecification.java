package org.psc.workerws.calculators.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class DefaultCalculatorSpecification {
    private String id;
    private BigDecimal startValue;
    private BigDecimal modifierValue;
    private BigDecimal randomValue;

    public DefaultCalculatorSpecification() {
        log.info("new DefaultCalculatorSpecification created at {}",
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    }
}
