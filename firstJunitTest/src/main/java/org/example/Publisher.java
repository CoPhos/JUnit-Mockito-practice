/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.pubsub;

import java.util.ArrayList;
import java.util.List;

// Adapted from a similar example in the Spock framework
public class Publisher {
    private final List<Subscriber> subscribers = new ArrayList<>();

    public void addSubscriber(Subscriber sub) {
        subscribers.add(sub);
    }

    // Want to test this method
    public void send(String message) {
        for (Subscriber sub : subscribers) {
            try {
                sub.receive(message);
            } catch (Exception ignored) {
                // evil, but what can you do?
            }
        }
    }
}
