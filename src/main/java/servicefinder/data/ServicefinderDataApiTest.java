package servicefinder.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

import servicefinder.data.configuration.CouchbaseConfiguration;

@EnableAutoConfiguration
@Configuration
@ComponentScan
@PropertySource("classpath:test.properties")
@Import(CouchbaseConfiguration.class)
public class ServicefinderDataApiTest {	
	
    public static void main(String[] args) {
        SpringApplication.run(ServicefinderDataApiTest.class, args);
    }
}
