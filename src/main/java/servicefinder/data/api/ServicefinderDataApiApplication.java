package servicefinder.data.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import servicefinder.data.api.configuration.CouchbaseConfiguration;
import servicefinder.data.api.configuration.JmsConfiguration;

@Configuration
@ComponentScan(basePackages={"servicefinder.data.api","geonames.importer.postalcode"})
@EnableAutoConfiguration
@EnableWebMvc
@Import({CouchbaseConfiguration.class, JmsConfiguration.class})
public class ServicefinderDataApiApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ServicefinderDataApiApplication.class, args);
    }
}
