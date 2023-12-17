package org.example;

/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
@Service // Can only autowire into a managed bean
public class PersonService {
    private final PersonRepository repository;

    @Autowired // Not required if only one constructor, but doesn't hurt
    public PersonService(PersonRepository repository) {
        this.repository = repository;
    }

    // ... methods that use the repository ...
}
