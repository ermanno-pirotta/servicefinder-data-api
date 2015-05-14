package servicefinder.data;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import servicefinder.data.configuration.CouchbaseConfiguration;

@SpringBootApplication
@Import(CouchbaseConfiguration.class)
@Profile("test")
public class ServicefinderDataApiTest {	
	
}
