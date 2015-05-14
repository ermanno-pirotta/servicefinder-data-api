package servicefinder.data.api;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import servicefinder.data.ServicefinderDataApiApplication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServicefinderDataApiApplication.class)
@WebAppConfiguration
@TestPropertySource("/application-test.properties")
public abstract class ControllerTest {
	protected MediaType contentType = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	protected MockMvc mockMvc;

	@Autowired
	protected WebApplicationContext webApplicationContext;

	protected ObjectMapper jsonMapper;

	protected String jsonOf(Object objectToConvert)
			throws JsonProcessingException {
		return this.jsonMapper.writeValueAsString(objectToConvert);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
		this.jsonMapper = new ObjectMapper();
		this.getRepository().deleteAll();
	}
	
	abstract protected CrudRepository<?,String> getRepository();
}
