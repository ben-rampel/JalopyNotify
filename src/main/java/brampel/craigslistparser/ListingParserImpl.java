package brampel.craigslistparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service("parser")
public class ListingParserImpl implements ListingParser {

    @Async
    public CompletableFuture<Listing> parseListing(URL link){
        String title = "";
        String desc = "";
        String make = "none";
        String model = "none";
        List<URL> images = new LinkedList<>();
        Map<String, String> attributes = new HashMap<>();
        int price = 0;
        int year = 0;
        long listingID = 0;
        Document document = null;

        try {
            document = Jsoup.connect(
                    link.toString())
                    .timeout(0).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Parse brampel.craigslistparser.Listing Images to a List of URLs
        Elements links =
                Objects.requireNonNull(document).getElementsByTag("a");
        for (Element element : links) {
            if (element.className().equals("thumb")) {
                try {
                    images.add(new URL(element.attr("href")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }

        Elements attributesList = document.getElementsByClass("attrgroup");

        //get year, make, and model
        String[] YMM = new String[0];
        if(attributesList.size() > 0  && attributesList.get(0).children().size() > 0) {
            YMM = attributesList.get(0).children().get(0).text().split(" ");
        }
        if(YMM.length >= 3) {
            year = Integer.parseInt(YMM[0]);
            make = YMM[1];
            model = YMM[2];
        }
        if(attributesList.size() > 0) {
            for (Element attribute : attributesList.get(1).children()) {
                for (Element span : attribute.children()) {
                    String[] attrs = attribute.text().split(":");
                    attrs[1] = attrs[1].substring(1);
                    attributes.put(attrs[0], attrs[1]);
                }
            }
        } else {
            return null;
        }

        //Parse Title and Description
        Elements allElements =
                document.getAllElements();
        for (Element element : allElements) {
            if (element.id().equals("postingbody")) {
                desc = element.ownText();
            }
            if (element.id().equals("titletextonly")) {
                title = element.text();
            }
        }

        Elements spans =
                document.getElementsByTag("span");
        for(Element span : spans){
            if(span.className().equals("price")) {
                try {
                    price = Integer.parseInt(span.text().substring(1));
                } catch(Exception e){
                    return null;
                }
            }
        }

        //parse date
        Elements time = document.getElementsByTag("time");
        String date = time.get(0).text().substring(0,10);
        LocalDate d = LocalDate.parse(date);

        for(Element postinginfo : document.getElementsByClass("postinginfo")){
            if(postinginfo.text().contains("post id:")){
                listingID = Long.parseLong(postinginfo.text().substring(9));
            }
        }
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        if(attributes.get("odometer") != null) {
            attributes.put("odometer",
                    formatter.format(Integer.parseInt(attributes.get("odometer")))
            );
        }


        return CompletableFuture.completedFuture(new Listing(title, price, desc,model,make,year,attributes,images, d, link, listingID));
    }
}
