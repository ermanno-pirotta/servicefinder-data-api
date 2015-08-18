package servicefinder.data.api.builders;

import servicefinder.data.api.category.Category;
import servicefinder.data.api.quote.QuoteRequest;

public class QuoteRequestTestBuilder {
	public static QuoteRequest buildTestQuote(){
		QuoteRequest quote = new QuoteRequest();
		quote.setEmail("test@test.test");
		quote.setName("pippo");
		quote.setSurname("pluto");
		quote.setPlaceName("palosco");
		quote.setPostalCode("24050");
		Category testCategory = CategoryTestBuilder.buildTestCategory(CategoryTestBuilder.DEFAULT_NAME);
		
		quote.setCategoryName(testCategory.getName());
		quote.setRequestedServices(testCategory.getServices());
		
		return quote;
	}
}
