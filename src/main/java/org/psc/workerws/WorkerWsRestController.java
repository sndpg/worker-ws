package org.psc.workerws;

import org.psc.workerws.calculators.DefaultCalculator;
import org.psc.workerws.calculators.domain.SimpleCalculatorSpecification;
import org.psc.workerws.generators.UuidGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

@RestController("/service")
public class WorkerWsRestController {

    private final UuidGenerator uuidGenerator;

    private final DefaultCalculator defaultCalculator;

    public WorkerWsRestController(UuidGenerator uuidGenerator, DefaultCalculator defaultCalculator) {
        this.uuidGenerator = uuidGenerator;
        this.defaultCalculator = defaultCalculator;
    }

    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("{\"status\":\"OK\"}", HttpStatus.OK);
    }

    @GetMapping("/randomNumber")
    public Double getRandomNumber() {
        return new SecureRandom().nextDouble();
    }

    @GetMapping("/functional/mono/randomNumber")
    public Mono<Double> getRandomNumberFunctionallyAsMono() {
        return Mono.fromSupplier(() -> new SecureRandom().nextDouble());
    }

    @GetMapping(value = "/functional/flux/randomNumber", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Double> getRandomNumberFunctionallyAsFlux() {
        return Flux.fromStream(Stream.generate(() -> new SecureRandom().nextDouble()))
                .delayElements(Duration.ofMillis(100));
    }

    @GetMapping("/flux/randomNumber")
    public ResponseEntity<Flux<Double>> getRandomNumberAsFlux() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.fromStream(Stream.generate(() -> new SecureRandom().nextDouble()).limit(500)));
    }

    @GetMapping(value = "/functional/flux/time", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<LocalDateTime> getTimeFunctionally() {
        return Flux.fromStream(Stream.generate(LocalDateTime::now)).delayElements(Duration.ofMillis(250));
    }

    @GetMapping(value = "/uuid", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, UUID> getUuid() {
        var first = uuidGenerator.getUuid();
        var second = uuidGenerator.getUuid();
        return Map.of("first", first, "second", second);
    }

    @PostMapping(value = "/defaultCalculation", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, BigDecimal> uselessCalculate(@RequestBody SimpleCalculatorSpecification specification) throws NoSuchAlgorithmException {
        var id = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-hh:mm:ss.nnnnnnnnn"));
        var result = defaultCalculator.calculate(id, specification.getStartValue(), specification.getModifierValue());
        return Map.of(id, result);
    }
}