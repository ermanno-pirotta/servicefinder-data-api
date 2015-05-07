package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import servicefinder.data.ServicefinderDataApiTest;
import servicefinder.data.model.Category;
import servicefinder.data.model.CategoryRepository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author piro84
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServicefinderDataApiTest.class)
@WebAppConfiguration
public class CategoryControllerTests{

 private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
	            MediaType.APPLICATION_JSON.getSubtype(),
	            Charset.forName("utf8"));	
	
  private MockMvc mockMvc;  
  
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private CategoryRepository categoryRepository;
  
  private ObjectMapper jsonMapper;
  
  @Before
  public void setup() throws Exception {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    this.jsonMapper = new ObjectMapper();
  }        

  @Test
  public void shouldAddANewCategory() throws Exception{
	  Category category = createTestCategory("test");
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
			              .content(this.jsonOf(createTestCategory(null))));
  }  
  
  @Test
  public void shouldGiveAConflictWhenCreatingWithExistingName() throws Exception{	  
	  Category category = createTestCategory("test");	  
	  this.categoryRepository.save(category);
	  
	  this.mockMvc.perform(post("/categories")
			  			  .contentType(contentType)
			  			  .content(this.jsonOf(category)))
			  		.andExpect(status().isConflict());
	  
	  categoryRepository.delete(category);
  }
  
  @Test
  public void shouldReturnAListOfCategories() throws Exception{
	  List<Category> categories = createTestCategoryList("test1", "test2"); 
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
	  List<Category> categories = createTestCategoryList("test1", "test2"); 
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
	  this.mockMvc.perform(get("/categories/test1")
				.contentType(contentType))
	  .andExpect(status().isNoContent());	  	 
  }
  
  
  private Category createTestCategory(String name){
	  return new Category(name, Arrays.asList(new String[]{"test service 1","test service 2"}));
  }   
  
  private List<Category> createTestCategoryList(String... names){
	  List<Category> categoryList = new ArrayList<Category>(names.length);
	  
	  for(String name : names){
		  categoryList.add(createTestCategory(name));
	  }
	  
	  return categoryList;
  }  
  
  private String jsonOf(Object objectToConvert) throws JsonProcessingException{
	  return this.jsonMapper.writeValueAsString(objectToConvert);
  }
}