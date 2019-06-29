package org.psc.workerws.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Configuration
public class PersonsInitializer {

    private final PersonRepository personRepository;

    @Bean
    public CommandLineRunner addPersons() {
        return args -> {
            personRepository.deleteAll();
            Stream.of("Abc Def", "Vxc Wert", "Opld Mnbv", "Lopl Zuijk")
                    .map(e -> new Person(e.split(" ")[0], e.split(" ")[1], LocalDate.now()
                            .minusDays(new Random().nextInt(50))
                            .minusMonths(new Random().nextInt(12))
                            .minusYears(new Random().nextInt(55)), LocalDateTime.now()))
                    .forEach(personRepository::save);
        };
    }
}
