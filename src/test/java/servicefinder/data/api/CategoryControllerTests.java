package servicefinder.data.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.Arrays;

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
    
    this.categoryRepository.deleteAll();
  }       

  @Test
  public void shouldAddANewCategory() throws Exception{
	  String categoryJson = this.createTestCategoryAsJson("test");

	  this.mockMvc.perform(post("/categories")
			              .contentType(contentType)
			              .content(categoryJson))
				  .andExpect(status().isCreated())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(content().json(categoryJson));
  }
  
  @Test(expected=IllegalArgumentException.class)
  public void shouldThrownAnExceptionWhenNameIsMissing() throws Exception{
	  
	  this.mockMvc.perform(post("/categories")
			              .contentType(contentType)
			              .content(this.createTestCategoryAsJson(null)));
  }  
  
  @Test
  public void shouldGiveAConflictWhenCreatingWithExistingName() throws Exception{
	  final String categoryName = "test";
	  
	  this.categoryRepository.save(this.createTestCategory(categoryName));
	  
	  this.mockMvc.perform(post("/categories")
			  			  .contentType(contentType)
			  			  .content(this.createTestCategoryAsJson(categoryName)))
			  		.andExpect(status().isConflict());
  }  
  
  private String createTestCategoryAsJson(String name) throws JsonProcessingException{
	  return this.jsonMapper.writeValueAsString(this.createTestCategory(name));
  }
  
  private Category createTestCategory(String name){
	  return new Category(name, Arrays.asList(new String[]{"test service 1","test service 2"}));
  }
}