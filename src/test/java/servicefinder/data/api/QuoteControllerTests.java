package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import servicefinder.data.api.builders.QuoteRequestTestBuilder;
import servicefinder.data.model.QuoteRequest;
import servicefinder.data.model.QuoteRequestRepository;

public class QuoteControllerTests extends ControllerTest {

	@Autowired
	QuoteRequestRepository quoteRequestRepository;

	@Test
	public void shouldSaveANewQuoteRequest() throws Exception{
		QuoteRequest quote = QuoteRequestTestBuilder.buildTestQuote();
		String quoteAsJson = jsonOf(quote);
		
		this.mockMvc.perform(post("/quotes")
							 .contentType(contentType)
							 .content(quoteAsJson))
							 .andExpect(status().isCreated())						 
							 .andExpect(content().contentType(contentType))
							 .andExpect(content().json(quoteAsJson));
		
		quoteRequestRepository.deleteAll();
	}
	
	@Override
	protected CrudRepository<?, String> getRepository() {
		return quoteRequestRepository;
	}
}