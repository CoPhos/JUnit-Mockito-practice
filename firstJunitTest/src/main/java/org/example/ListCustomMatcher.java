/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
// custom matcher that implements the ArgumentMatcher interface
class ListOfTwoElements implements ArgumentMatcher<List> {
    public boolean matches(List list) {
        return list.size() == 2;
    }

    public String toString() {
        //printed in verification errors
        return "[list of 2 elements]";
    }
}

    // create the mock
    List mock = mock(List.class);

    // set the expectation using argThat and the custom matcher
    when(mock.addAll(argThat(new ListOfTwoElements()))).thenReturn(true);

// somewhere in the actual test, test a method that invokes addAll
// with a two-element list:
    mock.addAll(Arrays.asList("one","two"));

// verify that the test called addAll with the custom matcher
    verify(mock).addAll(argThat(new ListOfTwoElements()));
