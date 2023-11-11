/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
public interface PersonRepository {
    Person save(Person person);

    Optional<Person> findById(int id);

    List<Person> findAll();

    long count();

    void delete(Person person);
}
