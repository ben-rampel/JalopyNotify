package brampel.craigslistparser;

import java.net.URL;
import java.util.concurrent.CompletableFuture;

public interface ListingParser {
    CompletableFuture<Listing> parseListing(URL link);
}
