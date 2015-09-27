package servicefinder.data.api.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.api.http.HttpHeaderBuilder;

@RestController
@RequestMapping("/businesses")
public class BusinessController {
	
	@Autowired
	private HttpHeaderBuilder headerBuilder;
	
	@Autowired
	private BusinessRepository businessRepository;
		
	@RequestMapping(value="/{id}",method = RequestMethod.GET)	
	ResponseEntity<?> getBusiness(@PathVariable String id){
		Business business = businessRepository.findOne(id);	
		
		if(business != null){
			return new ResponseEntity<>(business, headerBuilder.buildHttpHeadersForResource(business), HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> subscribe(@RequestBody Business inputBusiness){		
		if(!isValidBusiness(inputBusiness)){
			return new ResponseEntity<>(inputBusiness, headerBuilder.buildHttpHeadersForResource(inputBusiness), HttpStatus.BAD_REQUEST);
		}
		
		String id = Business.buildIdFromFiscalCode(inputBusiness.getFiscalCode());		
		
		Business business = businessRepository.findOne(id);		
		if(business != null){
			return new ResponseEntity<>(business, headerBuilder.buildHttpHeadersForResource(business), HttpStatus.CONFLICT);
		}
		else{			
			business = businessRepository.save(inputBusiness);
			
			return new ResponseEntity<>(business, headerBuilder.buildHttpHeadersForResource(business), HttpStatus.CREATED);
		}
	}	
	
	@RequestMapping(value="/{id}",method = RequestMethod.DELETE)	
	ResponseEntity<?> unsubscribe(@PathVariable String id){
		Business business = businessRepository.findOne(id);	
		
		if(business != null){
			businessRepository.delete(business);
			return new ResponseEntity<>(headerBuilder.buildHttpHeadersForResource(business), HttpStatus.ACCEPTED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
		}
	}
	
	//TODO: use validators instead of re-inventing the wheel!
	private boolean isValidBusiness(Business input){
		if(input == null 
				|| StringUtils.isBlank(input.getName())
				|| StringUtils.isBlank(input.getFiscalCode())){
			return false;
		}
		
		return true;
	}
}
