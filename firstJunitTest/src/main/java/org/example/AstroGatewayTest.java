/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.astro;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AstroGatewayTest {
    private final Gateway<AstroResponse> gateway = new AstroGateway();

    @Test
    void testDeserializeToRecords() {
        AstroResponse result = gateway.getResponse();
        result.getPeople().forEach(System.out::println);
        assertAll(
            () -> assertTrue(result.getNumber() >= 0),
            () -> assertEquals(result.getPeople().size(), result.getNumber())
        );
    }
}