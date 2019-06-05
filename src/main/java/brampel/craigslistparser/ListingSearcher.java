package brampel.craigslistparser;

import java.util.List;

public interface ListingSearcher {
    List<Listing> getListings();
    void search();
}
