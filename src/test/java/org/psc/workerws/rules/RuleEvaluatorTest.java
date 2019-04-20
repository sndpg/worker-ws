package org.psc.workerws.rules;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootTest
public class RuleEvaluatorTest {

    @Value("classpath:rule1.groovy")
    private Resource resource;

    @org.junit.jupiter.api.Test
    public void testEvaluate() throws IOException {
        resource.getDescription();
        RuleEvaluator ruleEvaluator = new RuleEvaluator();
        org.junit.jupiter.api.Assertions.assertTrue(ruleEvaluator.evaluate());
    }
}
