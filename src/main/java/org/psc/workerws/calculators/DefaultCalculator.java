package org.psc.workerws.calculators;

import lombok.extern.slf4j.Slf4j;
import org.psc.workerws.calculators.domain.DefaultCalculatorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

@Slf4j
@Component
public class DefaultCalculator {

    @Autowired
    private DefaultCalculatorSpecification specification;

    public BigDecimal calculate(String id, BigDecimal startValue, BigDecimal modifier) throws NoSuchAlgorithmException {

        specification.setId(id);
        specification.setStartValue(startValue);
        specification.setModifierValue(modifier);

        var result = calculate(specification);
        log.info("randomValue = {}", specification.getRandomValue());

        return result;
    }

    private BigDecimal calculate(DefaultCalculatorSpecification specification) throws NoSuchAlgorithmException {
        var intermediate = specification.getStartValue().multiply(specification.getModifierValue());
        if (intermediate.compareTo(BigDecimal.ZERO) < 0) {
            specification.setRandomValue(BigDecimal.valueOf(SecureRandom.getInstanceStrong().nextDouble()));
            intermediate = intermediate.add(specification.getRandomValue());
        } else {
            specification.setRandomValue(BigDecimal.ZERO);
        }
        return intermediate;
    }

}
