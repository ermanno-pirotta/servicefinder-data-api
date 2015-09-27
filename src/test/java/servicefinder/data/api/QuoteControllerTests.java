package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import servicefinder.data.api.builders.QuoteRequestTestBuilder;
import servicefinder.data.api.quote.QuoteRequest;
import servicefinder.data.api.quote.QuoteRequestRepository;

import com.fasterxml.jackson.core.JsonProcessingException;

public class QuoteControllerTests extends ControllerTest {

	@Autowired
	QuoteRequestRepository quoteRequestRepository;

	@Test
	public void shouldSaveANewQuoteRequest() throws Exception{
		QuoteRequest quote = new QuoteRequestTestBuilder().build();
		String quoteAsJson = jsonOf(quote);
		
		this.mockMvc.perform(post("/quotes")
							 .contentType(contentType)
							 .content(quoteAsJson))
							 .andExpect(status().isCreated())						 
							 .andExpect(content().contentType(contentType))
							 .andExpect(content().json(quoteAsJson));
		
		quoteRequestRepository.deleteAll();
	}
	
	 @Test
	  public void shouldReturnASpecificQuoteRequest() throws JsonProcessingException, Exception{
		  QuoteRequest request = new QuoteRequestTestBuilder().build();
		  this.quoteRequestRepository.save(request);
		  
		  this.mockMvc.perform(get("/quotes/" + QuoteRequest.buildIdFromTimestamp(request.getCreationTimestamp()))
				  				.contentType(contentType))
				  	  .andExpect(status().isOk())
				  	  .andExpect(content().contentType(contentType))
				  	  .andExpect(content().json(this.jsonOf(request)));		
		  
		  quoteRequestRepository.delete(request);
	  }
	
	@Override
	protected CrudRepository<?, String> getRepository() {
		return quoteRequestRepository;
	}
}