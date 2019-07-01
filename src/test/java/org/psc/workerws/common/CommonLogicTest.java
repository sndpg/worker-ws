package org.psc.workerws.common;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CommonLogicTest {

    @Test
    public void testSortDefaultValues(){
        Map<Integer, String> sortedMap = CommonLogic.sortDefaultValuesByKeyAscending();

        assertThat(sortedMap.keySet().iterator().next(), is(1));
    }

    @Test
    public void testSortDefaultValues2(){
        Map<Integer, String> sortedMap = CommonLogic.sortDefaultValuesByValuesDescending();

        assertThat(sortedMap.values().iterator().next(), is("xyz"));
    }
}
