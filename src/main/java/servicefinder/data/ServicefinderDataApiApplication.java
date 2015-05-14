package servicefinder.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import servicefinder.data.configuration.CouchbaseConfiguration;
import servicefinder.data.configuration.JmsConfiguration;

@SpringBootApplication
@EnableWebMvc
@Import({CouchbaseConfiguration.class, JmsConfiguration.class})
public class ServicefinderDataApiApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ServicefinderDataApiApplication.class, args);
    }
}
