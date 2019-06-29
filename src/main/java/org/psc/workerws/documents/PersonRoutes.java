package org.psc.workerws.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@RequiredArgsConstructor
@Configuration
public class PersonRoutes {

    private final PersonRepository personRepository;

    @Bean
    public RouterFunction<ServerResponse> personsRoute() {
        return route(RequestPredicates.GET("/reactive/persons"), this::getAllPersons).andRoute(
                RequestPredicates.GET("/reactive/persons/{id}"), this::getById);
    }

    private Mono<ServerResponse> getAllPersons(ServerRequest request) {
        var allPersons = Flux.fromStream(personRepository.getAllPersons());
        return ok().body(allPersons, Person.class);
    }

    private Mono<ServerResponse> getById(ServerRequest request) {
        var person = Mono.fromFuture(personRepository.getById(request.pathVariable("id")));
        return ok().body(person, Person.class);
    }
}
