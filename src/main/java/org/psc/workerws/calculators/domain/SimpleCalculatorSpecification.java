package org.psc.workerws.calculators.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SimpleCalculatorSpecification {
    private BigDecimal startValue;
    private BigDecimal modifierValue;
}
