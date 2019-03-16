package org.psc.workerws;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.stream.Stream;

@RestController("/service")
public class WorkerWsRestController {

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
}