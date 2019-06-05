package brampel.craigslistparser;

public class ListingNotFoundException extends RuntimeException {
    public ListingNotFoundException(long id){
        super("Could not find listing of id " + id);
    }
}
