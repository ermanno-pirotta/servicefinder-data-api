package servicefinder.data.api.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.api.category.Category;
import servicefinder.data.api.category.CategoryRepository;
import servicefinder.data.api.http.HttpHeaderBuilder;

@RestController
@RequestMapping("/search")
public class SearchController {
	@Autowired
	private HttpHeaderBuilder headerBuilder;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(value="/services/autocomplete", method = RequestMethod.GET)		
	ResponseEntity<?> getAutocompleteSuggestions(){
		return new ResponseEntity<>(autocompleteSuggestions(), headerBuilder.buildHttpHeaders(),HttpStatus.OK);
	}
	
	private List<AutocompleteHint> autocompleteSuggestions(){
		Iterable<Category> categories = categoryRepository.findAll();
		List<AutocompleteHint> suggestions = new ArrayList<AutocompleteHint>();
		
		for(Category category : categories){
			for(String service: category.getServices()){
				suggestions.add(new AutocompleteHint(service));
			}
		}
		
		return suggestions;
	}
	
}
