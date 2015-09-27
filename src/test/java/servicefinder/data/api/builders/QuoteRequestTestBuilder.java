package servicefinder.data.api.builders;

import servicefinder.data.api.category.Category;
import servicefinder.data.api.quote.QuoteRequest;

public class QuoteRequestTestBuilder {
	private QuoteRequest instance;
	
	public QuoteRequestTestBuilder(){
		this.instance = new QuoteRequest();
		setDefaults();
	}
	
	public QuoteRequest build(){
		return this.instance;
	}
	
	public QuoteRequestTestBuilder withEmail(String email){
		this.instance.setEmail(email);
		return this;
	}
	
	public QuoteRequestTestBuilder withName(String name){
		this.instance.setName(name);
		return this;
	}
	
	public QuoteRequestTestBuilder withSurname(String surname){
		this.instance.setSurname(surname);
		return this;
	}
	
	public QuoteRequestTestBuilder withPlaceName(String placeName){
		this.instance.setPlaceName(placeName);
		return this;
	}
	
	public QuoteRequestTestBuilder withPostalCode(String postalCode){
		this.instance.setPostalCode(postalCode);
		return this;
	}
	
	public QuoteRequestTestBuilder withCategoryName(String categoryName){
		this.instance.setCategoryName(categoryName);
		return this;
	}
	
	private void setDefaults(){
		this.instance.setEmail("test@test.test");
		this.instance.setName("pippo");
		this.instance.setSurname("pluto");
		this.instance.setPlaceName("palosco");
		this.instance.setPostalCode("24050");
		Category testCategory = CategoryTestBuilder.buildTestCategory(CategoryTestBuilder.DEFAULT_NAME);
		
		this.instance.setCategoryName(testCategory.getName());
		this.instance.setRequestedServices(testCategory.getServices());
	}
	
}
