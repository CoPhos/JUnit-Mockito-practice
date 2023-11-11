/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
@SpringBootTest
class PersonServiceTest {
    @MockBean // Instantiate mock and put into the application context
    private PersonRepository repository;

    @Test
    public void findMaxId() {
        // Set expectations on mock
        when(repository.findAll()).thenReturn(people);

        // Test service method
        assertEquals(14, service.getHighestId().intValue());

        // Verify method call on mock
        verify(repository).findAll();
    }
}
