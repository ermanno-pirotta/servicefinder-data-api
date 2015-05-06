package servicefinder.data.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import servicefinder.data.configuration.ServicefinderDataApiApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServicefinderDataApiApplication.class)
@WebAppConfiguration
public class ServicefinderDataApiApplicationTests {

	@Test
	public void contextLoads() {
	}

}
