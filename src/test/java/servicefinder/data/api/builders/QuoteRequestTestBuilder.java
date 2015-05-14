package servicefinder.data.api.builders;

import servicefinder.data.model.QuoteRequest;

public class QuoteRequestTestBuilder {
	public static QuoteRequest buildTestQuote(){
		QuoteRequest quote = new QuoteRequest();
		quote.setEmail("test@test.test");
		quote.setName("pippo");
		quote.setSurname("pluto");			
		
		quote.setCategory(CategoryTestBuilder.buildTestCategory(CategoryTestBuilder.DEFAULT_NAME));
		quote.setRequestedServices(quote.getCategory().getServices());
		
		return quote;
	}
}
