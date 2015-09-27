package servicefinder.data.api.quote;

import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.api.common.HttpHeaderBuilder;
import servicefinder.data.api.configuration.JmsConfiguration;

@RestController
@RequestMapping("/quotes")
public class QuoteRequestController {
			
	private final Logger logger = LoggerFactory.getLogger(QuoteRequestController.class);
	
	@Autowired
	private HttpHeaderBuilder headerBuilder;
	
	@Autowired
	private QuoteRequestRepository quoteRequestRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(method = RequestMethod.GET, value="/{id}")	
	ResponseEntity<?> get(@PathVariable String id){
		QuoteRequest quote = quoteRequestRepository.findOne(id);	
		
		if(quote != null){
			return new ResponseEntity<>(quote, headerBuilder.buildHttpHeadersForResource(quote), HttpStatus.OK);
		}
		else{ 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody QuoteRequest request){
		logger.info(String.format("New quote for category %s from user with email %s", request.getCategoryName(), request.getEmail()));
		QuoteRequest quote = quoteRequestRepository.save(request);
		
		if(quote == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		this.sendQuoteRequestNotification(quote);
		
		return new ResponseEntity<>(quote,headerBuilder.buildHttpHeadersForResource(quote), HttpStatus.CREATED);		
	}
	
	
	
	private void sendQuoteRequestNotification(QuoteRequest quote){
		logger.info(String.format("Publishing event for quote request (category = %s, email = %s)", quote.getCategoryName(), quote.getEmail()));
		 // Send a message
		jmsTemplate.convertAndSend(JmsConfiguration.QUOTES_DESTINATION_NAME, quote);
		
		logger.info(String.format("Event for quote request (category = %s, email = %s) successfully published", quote.getCategoryName(), quote.getEmail()));
		
	}
}