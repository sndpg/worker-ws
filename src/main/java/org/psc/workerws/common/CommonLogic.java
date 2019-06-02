package org.psc.workerws.common;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;
import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;

public class CommonLogic {

    private static final Map<Integer, String> someValues =
            Map.of(10, "someValue", 200, "anotherValue", 1, "a different value", 981, "xyz", 133, "more values", 101,
                    "ok");


    public static Map<Integer, String> sortDefaultValues() {
        return null;
    }

    public static Map<Integer, String> sortDefaultValuesByValuesDescending() {
        return someValues.entrySet()
                .stream()
                .sorted(reverseOrder(comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static Map<Integer, String> sortDefaultValuesByKeyAscending() {
        return someValues.entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
    }

    public static <K, V> Map<K, V> sortMap(Map<K, V> map) {
        return null;
    }


}
