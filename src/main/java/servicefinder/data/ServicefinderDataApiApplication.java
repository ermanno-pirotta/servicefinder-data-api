package servicefinder.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import servicefinder.data.configuration.CouchbaseConfiguration;

@EnableAutoConfiguration
@EnableWebMvc
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
@Import(CouchbaseConfiguration.class)
public class ServicefinderDataApiApplication {
	
    public static void main(String[] args) {
        SpringApplication.run(ServicefinderDataApiApplication.class, args);
    }
}
