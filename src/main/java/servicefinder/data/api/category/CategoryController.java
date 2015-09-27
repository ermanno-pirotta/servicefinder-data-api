package servicefinder.data.api.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.api.http.HttpHeaderBuilder;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	@Autowired
	private HttpHeaderBuilder headerBuilder;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(method = RequestMethod.GET)		
	ResponseEntity<?> getAll(){
		return new ResponseEntity<>(categoryRepository.findAll(), headerBuilder.buildHttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}",method = RequestMethod.GET)	
	ResponseEntity<?> getCategory(@PathVariable String id){
		Category category = categoryRepository.findOne(id);	
		
		if(category != null){
			return new ResponseEntity<>(category, headerBuilder.buildHttpHeadersForResource(category), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(@RequestBody Category inputCategory){				
		String id = Category.buildIdFromName(inputCategory.getName());		
		
		Category category = categoryRepository.findOne(id);		
		if(category != null){
			return new ResponseEntity<>(category, headerBuilder.buildHttpHeadersForResource(category), HttpStatus.CONFLICT);
		}
		else{			
			category = categoryRepository.save(inputCategory);
			
			return new ResponseEntity<>(category, headerBuilder.buildHttpHeadersForResource(category), HttpStatus.CREATED);
		}
	}
	
}
