/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.pubsub;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

class PublisherTest {
    private final Publisher pub = new Publisher();
    private final Subscriber sub1 = mock(Subscriber.class);
    private final Subscriber sub2 = mock(Subscriber.class);

    @BeforeEach
    void setUp() {
        pub.addSubscriber(sub1);
        pub.addSubscriber(sub2);
    }


    @Test
    void publisherSendsMessageToAllSubscribers() {
        pub.send("Hello");

        verify(sub1).receive("Hello");
        verify(sub2).receive("Hello");
    }

    @Test
    void testSendInOrder() {
        pub.send("Hello");

        InOrder inorder = inOrder(sub1, sub2);
        inorder.verify(sub1).receive("Hello");
        inorder.verify(sub2).receive("Hello");
    }

    @Test
    void publisherSendsMessageWithAPattern() {
        pub.send("Message 1");
        pub.send("Message 2");

        // Check for any string
        verify(sub1, times(2)).receive(anyString());
        verify(sub2, times(2)).receive(anyString());

        // Alternatively, check for a specific pattern
        verify(sub1, times(2)).receive(
            argThat(s -> s.matches("Message \\d")));
        verify(sub1, times(2)).receive(
            argThat(s -> s.matches("Message \\d")));
    }

    @Test
    void handleMisbehavingSubscribers() {
        // sub1 throws an exception
        doThrow(RuntimeException.class).when(sub1).receive(anyString());

        pub.send("message 1");
        pub.send("message 2");

        // both subscribers still received the messages
        verify(sub1, times(2)).receive(anyString());
        verify(sub2, times(2)).receive(anyString());
    }
}
