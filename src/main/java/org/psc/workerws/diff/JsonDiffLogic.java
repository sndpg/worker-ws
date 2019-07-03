package org.psc.workerws.diff;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JsonDiffLogic {

    private final ObjectMapper objectMapper;

    public String doDiff(String first, String second) throws IOException {
        var source = objectMapper.readTree(first);
        var target = objectMapper.readTree(second);
        var pathNode = JsonDiff.asJson(source, target);

        return pathNode.toString();
}
}