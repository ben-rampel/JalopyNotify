package brampel.craigslistparser;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Listing {
    private final String title;
    private final int price;
    private final String description;
    private final String model;
    private final String make;
    private final int year;
    private final Map<String,String> attributes;
    private final List<URL> images;
    private final LocalDate postDate;
    private final URL link;
    private final long listingID;

    public Listing(String title, int price, String description, String model, String make, int year, Map<String, String> attributes, List<URL> images, LocalDate postDate, URL link, long listingID) {
        this.title = title;
        this.price = price;
        this.description = description;
        this.model = model;
        this.make = make;
        this.year = year;
        this.attributes = attributes;
        this.images = images;
        this.postDate = postDate;
        this.link = link;
        this.listingID = listingID;
    }

    public long getListingID() {
        return listingID;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getModel() {
        return model;
    }

    public String getMake() {
        return make;
    }

    public int getYear() {
        return year;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<URL> getImages() {
        return images;
    }

    public LocalDate getPostDate() {
        return postDate;
    }

    public URL getLink() {
        return link;
    }
}
