package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import servicefinder.data.api.category.Category;
import servicefinder.data.api.category.CategoryBuilder;
import servicefinder.data.api.category.CategoryRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;

public class CategoryControllerTests extends ControllerTest{  

  @Autowired
  private CategoryRepository categoryRepository;
   
  @Test
  public void shouldAddANewCategory() throws Exception{
	  Category category = new CategoryBuilder().withName("test-category").withServices(Lists.newArrayList("test-service1", "test-service2")).build();
	  String categoryJson = this.jsonOf(category);

	  this.mockMvc.perform(post("/categories")
			              .contentType(contentType)
			              .content(categoryJson))
				  .andExpect(status().isCreated())
              .andExpect(content().contentType(contentType))
              .andExpect(content().json(categoryJson));
	  
	  categoryRepository.delete(category);
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void shouldThrownAnExceptionWhenNameIsMissing() throws Exception{
	  
	  this.mockMvc.perform(post("/categories")
			              .contentType(contentType)
			              .content(jsonOf(new CategoryBuilder().withName(null).build())));
  }  
  
  @Test
  public void shouldGiveAConflictWhenCreatingWithExistingName() throws Exception{	  
	  Category category = new CategoryBuilder().withName("test-category").build();	  
	  this.categoryRepository.save(category);
	  
	  this.mockMvc.perform(post("/categories")
			  			  .contentType(contentType)
			  			  .content(this.jsonOf(category)))
			  		.andExpect(status().isConflict());
	  
	  categoryRepository.delete(category);
  }
  
  @Test
  public void shouldReturnAListOfCategories() throws Exception{
	  List<Category> categories = buildTestCategories("test1", "test2"); 
	  this.categoryRepository.save(categories);
	  
	  this.mockMvc.perform(get("/categories")
			  				.contentType(contentType))
			  	  .andExpect(status().isOk())
			  	  .andExpect(content().contentType(contentType))
			  	  .andExpect(content().json(this.jsonOf(categories)));		
	  
	  categoryRepository.delete(categories);
  }
  
  @Test
  public void shouldReturnASpecificCategory() throws JsonProcessingException, Exception{
	  List<Category> categories = buildTestCategories("test1", "test2"); 
	  this.categoryRepository.save(categories);
	  
	  Category categoryToBeRetrieved = categories.get(0);
	  
	  this.mockMvc.perform(get("/categories/" + Category.buildIdFromName(categoryToBeRetrieved.getName()))
			  				.contentType(contentType))
			  	  .andExpect(status().isOk())
			  	  .andExpect(content().contentType(contentType))
			  	  .andExpect(content().json(this.jsonOf(categoryToBeRetrieved)));		
	  
	  categoryRepository.delete(categories);
  }
  
  @Test
  public void shouldReturnANoContentWhenCategoryIsMissing() throws Exception{
	  this.mockMvc.perform(get("/categories/NotExisting")
				.contentType(contentType))
	  .andExpect(status().isNoContent());	  	 
  }    
  
	@Override
	protected CrudRepository<?, String> getRepository() {
		return categoryRepository;
	}     
	
	private List<Category> buildTestCategories(String... names){
		List<Category> categoryList = new ArrayList<Category>(names.length);

		for (String name : names) {
			categoryList.add(new CategoryBuilder().withName(name).build());
		}

		return categoryList;
	}
	
}