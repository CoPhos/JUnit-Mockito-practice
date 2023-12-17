package org.example;


public class PersonService {
    private final PersonRepository repository;

    public PersonService(PersonRespository repository) {
        this.repository = repository;
    }

    // methods to be tested...
}
