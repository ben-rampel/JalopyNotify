package brampel.craigslistparser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import java.io.BufferedInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service("searcher")
public class ListingSearcherImpl implements ListingSearcher {
    private List<Listing> listings;
    private ListingParser parser;
    private DocumentBuilder builder;
    private URL searchURL;

    @Autowired
    public ListingSearcherImpl(ListingParser parser, DocumentBuilder builder, URL searchURL){
        this.listings = new ArrayList<>();
        this.parser = parser;
        this.builder = builder;
        this.searchURL = searchURL;
    }

    @Override
    public List<Listing> getListings() {
        return listings;
    }

    @Override
    @PostConstruct
    public void search(){
        System.out.println("Searching craigslist...");
        List<NodeList> pages = new LinkedList<>();
        List<CompletableFuture<Listing>> futureListings = new LinkedList<>();
        try {

            NodeList cars = null;
            for(int index = 0; cars == null || cars.getLength() > 24; index += 25){
                URL indexedSearchURL = new URL(String.format(searchURL + "&s=%d",index));
                Document doc = builder.parse(new BufferedInputStream(indexedSearchURL.openStream()));
                doc.getDocumentElement().normalize();
                cars = doc.getElementsByTagName("item");
                pages.add(cars);
            }

            for(NodeList page : pages){
                for(int i = 0; i < page.getLength(); i++){
                    Element currentCar = (Element) page.item(i);
                    if(currentCar.getNodeType() == Node.ELEMENT_NODE){
                        futureListings.add(parser.parseListing(new URL(currentCar.getElementsByTagName("link").item(0).getTextContent())));
                    }
                }
            }

        } catch(Exception e) {
            e.printStackTrace();
        }
        //Wait for listing parsers to finish
        CompletableFuture.allOf(futureListings.toArray(new CompletableFuture[0])).join();

        //Add completed listings to the list
        for(CompletableFuture<Listing> futureListing : futureListings){
            try {
                if(futureListing.get() != null) {
                    listings.add(futureListing.get());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.printf("Parsed %d listings\n", listings.size());
    }
}
