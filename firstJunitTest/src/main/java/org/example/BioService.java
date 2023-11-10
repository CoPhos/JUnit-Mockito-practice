/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
package com.kousenit.wikipedia;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BioService {
    private final List<String> pageNames;

    public BioService(String... pageNames) {
        this.pageNames = Arrays.stream(pageNames)
            .collect(Collectors.toList());
    }

    public List<String> getBios() {
        return pageNames.stream()
            .map(WikiUtil::getWikipediaExtract)
            .collect(Collectors.toList());
    }
}
