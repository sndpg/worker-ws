package org.psc.workerws.documents;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@Document("persons")
public class Person {

    @Id
    private String id;

    private final String givenName;

    private final String lastName;

    private final LocalDate birthDate;

    private final LocalDateTime created;
}
