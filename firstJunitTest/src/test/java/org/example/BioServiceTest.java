/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.wikipedia;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

class BioServiceTest {
    @Test
    // Integration test
    void checkBios() {
        BioService service = new BioService("Anita Borg", "Ada Lovelace",
            "Grace Hopper", "Barbara Liskov");

        assertThat(service.getBios()).hasSize(4);
    }

    @Test
    void testBioServiceWithMocks() {
        BioService service = new BioService("Anita Borg", "Ada Lovelace",
            "Grace Hopper", "Barbara Liskov");

        // Use mockStatic in a try-with-resources block
        try (MockedStatic<WikiUtil> mocked = mockStatic(WikiUtil.class)) {

            // Same when/then methods as a regular mock
            mocked.when(() -> WikiUtil.getWikipediaExtract(anyString()))
                .thenAnswer(invocation -> "Bio for " +
                    invocation.getArgument(0));

            assertThat(service.getBios()).hasSize(4);

            // Verify using a MockedStatic.Verification argument
            mocked.verify(() -> WikiUtil.getWikipediaExtract(
                anyString()), times(4));
        }
    }
}