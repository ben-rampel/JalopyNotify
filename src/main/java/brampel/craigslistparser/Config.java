package brampel.craigslistparser;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@EnableScheduling
@EnableAsync
@ComponentScan
public class Config {

    @Bean
    public DocumentBuilder docBuilder() throws ParserConfigurationException {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder();
    }

    @Bean
    public URL searchURL() throws MalformedURLException {
        CraigslistSearchURLBuilder builder = new CraigslistSearchURLBuilder(200,27514);
        /*builder.setQuery("BMW");
        builder.setYearConstraint(1998,2006);
        builder.setModels("325*","330*");*/
        return builder.getURL();
    }

}
