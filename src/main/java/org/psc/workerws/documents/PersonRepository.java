package org.psc.workerws.documents;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface PersonRepository extends MongoRepository<Person, String> {

    @Query("{}")
    Stream<Person> getAllPersons();

    CompletableFuture<Person> getById(String id);
}
