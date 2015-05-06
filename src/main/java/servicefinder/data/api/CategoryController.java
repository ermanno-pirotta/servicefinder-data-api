package servicefinder.data.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import servicefinder.data.model.Category;
import servicefinder.data.model.CategoryRepository;

@RestController
@RequestMapping("/categories")
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> create(@RequestBody Category inputCategory){				
		String id = Category.buildIdFromName(inputCategory.getName());		
		
		Category category = null;
		category = categoryRepository.findOne(id);
		if(category != null){
			return new ResponseEntity<>(category, buildHttpHeadersForResource(category), HttpStatus.CONFLICT);
		}
		else{			
			category = categoryRepository.save(new Category(inputCategory.getName(), inputCategory.getServices()));
			
			return new ResponseEntity<>(category, buildHttpHeadersForResource(category), HttpStatus.CREATED);
		}
	}
	
	private HttpHeaders buildHttpHeadersForResource(Category resource){
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setLocation(ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(resource.getId()).toUri());
		
		return httpHeaders;
	}
}
