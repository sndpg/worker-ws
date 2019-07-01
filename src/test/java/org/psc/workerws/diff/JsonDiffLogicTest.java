package org.psc.workerws.diff;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JsonDiffLogicTest {

    @Autowired
    private JsonDiffLogic jsonDiffLogic;

    @Value("classpath:first.json")
    private Resource firstResource;

    @Value("classpath:second.json")
    private Resource secondResource;

    private String first;
    private String second;

    @BeforeEach
    void before() throws IOException {
        first = readInputStream(firstResource.getInputStream());
        second = readInputStream(secondResource.getInputStream());
    }

    @Test
    void doDiffTest() throws IOException {
        var diff = jsonDiffLogic.doDiff(first, second);
        assertThat(diff, is(not(nullValue())));
        log.info(diff);
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(1000);

        try (var reader = new BufferedReader(new InputStreamReader(inputStream))) {

            int current = 0;
            while ((current = reader.read()) != -1) {
                stringBuilder.append((char) current);
            }

        }

        return stringBuilder.toString();
    }
}