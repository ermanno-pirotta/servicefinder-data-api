package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import servicefinder.data.api.quote.QuoteRequest;
import servicefinder.data.api.quote.QuoteRequestBuilder;
import servicefinder.data.api.quote.QuoteRequestRepository;
import servicefinder.data.api.quote.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

public class QuoteControllerTests extends ControllerTest {

	@Autowired
	QuoteRequestRepository quoteRequestRepository;

	@Test
	public void shouldSaveANewQuoteRequest() throws Exception{
		QuoteRequest quote = buildQuoteWithDefaultValues();
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
		  QuoteRequest request = buildQuoteWithDefaultValues();
		  this.quoteRequestRepository.save(request);
		  
		  this.mockMvc.perform(get("/quotes/" + request.getCreationTimestamp().getTime())
				  				.contentType(contentType))
				  	  .andExpect(status().isOk())
				  	  .andExpect(content().contentType(contentType))
				  	  .andExpect(content().json(this.jsonOf(request, View.class)));		
		  
		  quoteRequestRepository.delete(request);
	  }
	 
	private QuoteRequest buildQuoteWithDefaultValues(){
		QuoteRequestBuilder builder = new QuoteRequestBuilder()
		.withEmail("test@test.test")
		.withName("pippo")
		.withSurname("pluto")
		.withPlaceName("palosco")
		.withPostalCode("24050")
		.withCategoryName("test-category")
		.withRequestedServices(Lists.newArrayList("test service 1", "test service 2"));
		
		return builder.build();
	}
	
	
	@Override
	protected CrudRepository<?, String> getRepository() {
		return quoteRequestRepository;
	}
}