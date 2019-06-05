package brampel.craigslistparser;

import java.net.MalformedURLException;
import java.net.URL;

public class CraigslistSearchURLBuilder {
    private String searchURL;

    public CraigslistSearchURLBuilder(int distance, int ZIPCode){
        searchURL = String.format("https://raleigh.craigslist.org/search/cta?search_distance=%d&postal=%d&bundleDuplicates=1&format=rss", distance, ZIPCode);
    }

    public void setPriceConstraint(Integer min, Integer max){
        searchURL += String.format("&min_price=%d&max_price=%d", min,max);
    }

    public void setQuery(String query){
        searchURL += String.format("&query=%s",query);
    }

    public void setYearConstraint(Integer min, Integer max){
        searchURL += String.format("&min_auto_year=%d&max_auto_year=%d", min,max);
    }

    public void setModels(String... models){
        StringBuilder modelString = new StringBuilder();
        for(int i = 0; i < models.length; i++){
            modelString.append(models[i]);
            if(i < models.length - 1) modelString.append("|");
        }
        searchURL += String.format("&auto_make_model=%s", modelString);
    }

    public void setMileageConstraint(Integer min, Integer max){
        searchURL += String.format("&min_auto_miles=%d&max_auto_miles=%d", min,max);
    }

    public URL getURL() throws MalformedURLException {
        return new URL(searchURL);
    }

}
