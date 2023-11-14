/***
 * Excerpted from "Mockito Made Clear",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material,
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose.
 * Visit https://pragprog.com/titles/mockito for more book information.
***/
import java.util.List;

public class FakeGateway implements Gateway<AstroResponse> {
    @Override
    public AstroResponse getResponse() {
        return new AstroResponse(7, "Success",
            List.of(new Assignment("Kathryn Janeway", "USS Voyager"),
                new Assignment("Seven of Nine", "USS Voyager"),
                new Assignment("Will Robinson", "Jupiter 2"),
                new Assignment("Lennier", "Babylon 5"),
                new Assignment("James Holden", "Rocinante"),
                new Assignment("Naomi Negata", "Rocinante"),
                new Assignment("Ellen Ripley", "Nostromo")));
    }
}
