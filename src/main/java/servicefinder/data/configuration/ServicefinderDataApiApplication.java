package servicefinder.data.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

@SpringBootApplication
public class ServicefinderDataApiApplication {

	 @EnableCouchbaseRepositories
		@Configuration
		static class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

			@Value("${couchbase.cluster.bucket}")
			private String bucketName;

			@Value("${couchbase.cluster.password}")
			private String password;

			@Value("${couchbase.cluster.ip}")
			private String ip;

			@Override
			protected List<String> bootstrapHosts() {
				return Arrays.asList(this.ip);
			}

			@Override
			protected String getBucketName() {
				return this.bucketName;
			}

			@Override
			protected String getBucketPassword() {
				return this.password;
			}					
		}
	
    public static void main(String[] args) {
        SpringApplication.run(ServicefinderDataApiApplication.class, args);
    }
}
