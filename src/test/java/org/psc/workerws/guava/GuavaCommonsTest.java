package org.psc.workerws.guava;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class GuavaCommonsTest {

    GuavaCommons guavaCommons = new GuavaCommons();

    @Test
    void testGenerateMultimap() {
        var result = guavaCommons.generateMultiMap();
        result.entries().forEach(e -> log.info("{}: {}", e.getKey().toString(), e.getValue()));
        assertThat(result.size(), is(greaterThan(1)));
    }

    @Test
    void testGenerateMutliSetException() {
        assertThrows(NullPointerException.class, () -> guavaCommons.generateMultiSet(null));
    }

    @Test
    void testGenerateMutliSet() {
        var result = guavaCommons.generateMultiSet("a", "b", "c", "a", "a");
        assertThat(result.size(), is(5));
    }

}