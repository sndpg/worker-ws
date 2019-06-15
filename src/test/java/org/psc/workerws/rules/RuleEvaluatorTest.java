package org.psc.workerws.rules;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class RuleEvaluatorTest {

    @Autowired
    private RuleEvaluator ruleEvaluator;

    @Test
    void testEvaluate() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate());
    }

    @Test
    void testEvaluate2() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate2());
    }

    @Test
    void testEvaluate3() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate3());
    }

    @Test
    void testEvaluate4() throws IOException {
        Assertions.assertTrue(ruleEvaluator.evaluate4());
    }
}

