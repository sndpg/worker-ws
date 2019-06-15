package org.psc.workerws.guava;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class GuavaCommons {

    public Multimap<SampleOption, String> generateMultiMap() {
        ListMultimap<SampleOption, String> someData =
                MultimapBuilder.enumKeys(SampleOption.class).arrayListValues(5).build();
        someData.put(SampleOption.OPTIONAL, "ifOptional");
        someData.put(SampleOption.OPTIONAL, "ifUnavailable");
        someData.put(SampleOption.VETOABLE, "never");
        someData.put(SampleOption.STICKY, "always");
        someData.put(SampleOption.STICKY, "excludeOlderThan2");
        return someData;
    }

    public Multiset<String> generateMultiSet(String... values) {
        Preconditions.checkNotNull(values, "values must not be null");
        return HashMultiset.create(List.of(values));
    }

}

