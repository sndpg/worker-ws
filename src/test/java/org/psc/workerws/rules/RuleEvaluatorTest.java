package org.psc.workerws.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

@SpringBootTest
public class RuleEvaluatorTest {

    @Autowired
    private RuleEvaluator ruleEvaluator;

    @Test
    public void testEvaluate() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate());
    }

    @Test
    public void testEvaluate2() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate2());
    }
}

