/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.hr;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

    @Mock
    private PersonRepository repository;

    @InjectMocks
    private PersonService service;

    private final List<Person> people = Arrays.asList(
        new Person(1, "Grace", "Hopper", LocalDate.of(1906, Month.DECEMBER, 9)),
        new Person(2, "Ada", "Lovelace", LocalDate.of(1815, Month.DECEMBER, 10)),
        new Person(3, "Adele", "Goldberg", LocalDate.of(1945, Month.JULY, 7)),
        new Person(14, "Anita", "Borg", LocalDate.of(1949, Month.JANUARY, 17)),
        new Person(5, "Barbara", "Liskov", LocalDate.of(1939, Month.NOVEMBER, 7)));

    // Can't be done because JUnit 5 extension is _strict_ and
    // many of these tests don't call repository.findAll()
//    @BeforeEach
//    void setUp() {
//        when(repository.findAll()).thenReturn(people);
//    }

    @Test
    public void findMaxId() {
        when(repository.findAll()).thenReturn(people);
        assertThat(service.getHighestId()).isEqualTo(14);
        verify(repository).findAll();
    }

    @Test
    public void findMaxId_BDD() {
        given(repository.findAll()).willReturn(people);
        assertThat(service.getHighestId()).isEqualTo(14);
        then(repository).should().findAll();
    }

    @Test
    void defaultImplementations() {
        PersonRepository mockRepo = mock(PersonRepository.class);
        assertAll(
            () -> assertNull(mockRepo.save(any(Person.class))),
            () -> assertTrue(mockRepo.findById(anyInt()).isEmpty()),
            () -> assertTrue(mockRepo.findAll().isEmpty()),
            () -> assertEquals(0, mockRepo.count())
        );
    }

    @Test
    void getLastNames_usingMockMethod() {
        // create a stub for the PersonRepository
        PersonRepository mockRepo = new PersonService(mockRepo);

        // Set the expectations on the mock...
        when(mockRepo.findAll()).thenReturn(people);

        // Inject the mock into the service
        PersonService personService = new PersonService(mockRepo);

        // Get the last names (this is the method to test)
        List<String> lastNames = personService.getLastNames();

        // Check that the last names are correct (using AssertJ)
        assertThat(lastNames)
            .contains("Borg", "Goldberg", "Hopper", "Liskov", "Lovelace");

        // Verify that the service called findAll on the mockRepo exactly once
        verify(mockRepo).findAll();
    }

    @Test
    public void getLastNames_usingAnnotations() {
        when(repository.findAll()).thenReturn(people);

        assertThat(service.getLastNames())
            .contains("Borg", "Goldberg", "Hopper", "Liskov", "Lovelace");

        verify(repository).findAll();
    }

    @Test
    public void getTotalPeople() {
        when(repository.count())
            .thenReturn((long) people.size());
        assertEquals(people.size(), service.getTotalPeople());
    }

    @Test
    public void saveAllPeople() {
        when(repository.save(any(Person.class)))
            .thenReturn(people.get(0),
                people.get(1),
                people.get(2),
                people.get(3),
                people.get(4));

        // test the service (which uses the mock)
        assertEquals(List.of(1, 2, 3, 14, 5),
            service.savePeople(people.toArray(Person[]::new)));

        // verify the interaction between the service and the mock
        verify(repository, times(people.size())).save(any(Person.class));
        verify(repository, never()).delete(any(Person.class));
    }

    @Test
    public void useAnswer() {
        // Lambda expression implementation of Answer<Person>
        when(repository.save(any(Person.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        List<Integer> ids = service.savePeople(people.toArray(Person[]::new));

        List<Integer> actuals = people.stream()
            .map(Person::getId)
            .collect(Collectors.toList());
        assertEquals(ids, actuals);
    }

    @Test
    public void savePersonThrowsException() {
        when(repository.save(any(Person.class)))
            .thenThrow(RuntimeException.class);

        assertThrows(RuntimeException.class, () -> service.savePeople(people.get(0)));
    }

    @Test
    public void createPerson() {
        when(repository.save(any(Person.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Person hopper = people.get(0);
        Person person = service.createPerson(
            hopper.getId(),
            hopper.getFirst(),
            hopper.getLast(),
            hopper.getDob());

        verify(repository).save(personArg.capture());

        assertEquals(personArg.getValue(), hopper);
        assertEquals(hopper, person);
    }

    @Captor
    private ArgumentCaptor<Person> personArg;

    @Test
    public void createPersonUsingDateString() {
        Person hopper = people.get(0);
        when(repository.save(hopper)).thenReturn(hopper);
        Person actual = service.createPerson(1, "Grace", "Hopper", "1906-12-09");

        verify(repository).save(personArg.capture());
        assertThat(personArg.getValue()).isEqualTo(hopper);
        assertThat(actual).isEqualTo(hopper);
    }

    @Test
    public void deleteAll() {
        when(repository.findAll()).thenReturn(people);

        doNothing().when(repository)
            .delete(any(Person.class));

        service.deleteAll();

        verify(repository, times(5)).delete(any(Person.class));
    }

    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    @Test
    public void deleteAllWithNulls() {
        // Set up findAll to return a list containing nulls of type Person
        when(repository.findAll()).thenReturn(
            Arrays.asList((Person) null));

        // This won't compile:
        // when(repository.delete(null)).thenThrow(RuntimeException.class);

        // But this will:
        doThrow(RuntimeException.class).when(repository).delete(null);

        assertThrows(RuntimeException.class, () -> service.deleteAll());

        verify(repository).delete(null);
    }

    @Test
    public void findByIdThatDoesNotExist() {
        when(repository.findById(anyInt()))
            .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(999);
        assertTrue(personList.isEmpty());

        verify(repository).findById(anyInt());
    }

    @Test
    @Disabled("Do not use argThat with integers")
    public void findByIdsThatDoNotExist_argThat() {
        when(repository.findById(argThat(id -> id > 14)))
            .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(15, 42, 78, 999);
        assertTrue(personList.isEmpty());

        verify(repository, times(4)).findById(anyInt());
    }

    @Test
    public void findByIdsThatDoNotExist_intThat() {
        // Custom matcher as lambda argument to intThat:
        when(repository.findById(intThat(id -> id > 14)))
            .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(15, 42, 78, 999);
        assertTrue(personList.isEmpty());

        verify(repository, times(4)).findById(anyInt());
    }

    @Test
    public void findByIdsThatDoExist() {
        when(repository.findById(anyInt()))
            .thenAnswer(invocation -> people.stream()
                .filter(person ->
                    invocation.getArgument(0)
                        .equals(person.getId()))
                .findFirst());

        List<Person> personList = service.findByIds(1, 3, 5);
        assertEquals(List.of(people.get(0), people.get(2), people.get(4)), personList);
    }

    @Test
    void findByIds_explicitWhens() {
        when(repository.findById(0))
            .thenReturn(Optional.of(people.get(0)));
        when(repository.findById(1))
            .thenReturn(Optional.of(people.get(1)));
        when(repository.findById(2))
            .thenReturn(Optional.of(people.get(2)));
        when(repository.findById(3))
            .thenReturn(Optional.of(people.get(3)));
        when(repository.findById(4))
            .thenReturn(Optional.of(people.get(4)));
        when(repository.findById(5))
            .thenReturn(Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);
        assertThat(personList).containsExactlyElementsOf(people);
    }

    @Test
    void findByIds_thenReturnWithMultipleArgs() {
        when(repository.findById(anyInt())).thenReturn(
            Optional.of(people.get(0)),
            Optional.of(people.get(1)),
            Optional.of(people.get(2)),
            Optional.of(people.get(3)),
            Optional.of(people.get(4)),
            Optional.empty());

        List<Person> personList = service.findByIds(0, 1, 2, 3, 4, 5);
        assertThat(personList).containsExactlyElementsOf(people);
    }

    @Test
    void testInMemoryPersonRepository() {
        PersonRepository personRepo = new InMemoryPersonRepository();
        PersonService personService = new PersonService(personRepo);

        personService.savePeople(people.toArray(Person[]::new));
        assertThat(personRepo.findAll()).isEqualTo(people);
    }

    @Test
    void testMultipleCalls() {
        when(repository.findById(anyInt()))
            .thenReturn(Optional.of(people.get(0)))
            .thenThrow(new IllegalArgumentException("Person with id not found"))
            .thenReturn(Optional.of(get(1)))
            .thenReturn(Optional.empty));

        // .. rest of test ...
    }

    @Test
    void testMockOfFinalMethod() {
        // Can mock a class containing final methods
        PersonRepository personRepo = mock(InMemoryPersonRepository.class);

        // Set the expectations on (final) save method in the mock
        when(personRepo.save(any(Person.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        // Inject the mock
        PersonService personService = new PersonService(personRepo);

        // Test the service
        List<Integer> ids = personService.savePeople(
            people.toArray(Person[]::new));
        assertThat(ids).containsExactly(1, 2, 3, 14, 5);

        // Verify the save method in the mock was invoked five times
        verify(personRepo, times(5)).save(any(Person.class));
    }

    @Test
    void spyOnRepository() {
        // Spy on the in-memory repository
        PersonRepository personRepo = spy(new InMemoryPersonRepository());
        PersonService personService = new PersonService(personRepo);

        personService.savePeople(people.toArray(Person[]::new));
        assertThat(personRepo.findAll()).isEqualTo(people);

        // Verify the method calls on the spy
        verify(personRepo, times(people.size())).save(any(Person.class));
    }
}