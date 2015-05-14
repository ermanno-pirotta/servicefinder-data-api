package servicefinder.data.api;

import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.configuration.JmsConfiguration;
import servicefinder.data.model.QuoteRequest;
import servicefinder.data.model.QuoteRequestRepository;

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
		
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody QuoteRequest request){
		logger.info(String.format("New quote for category %s from user with email %s", request.getCategory(), request.getEmail()));
		QuoteRequest quote = quoteRequestRepository.save(request);
		
		if(quote == null){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		this.sendQuoteRequestNotification(quote);
		
		return new ResponseEntity<>(quote,headerBuilder.buildHttpHeadersForResource(quote), HttpStatus.CREATED);		
	}
	
	private void sendQuoteRequestNotification(QuoteRequest quote){
		logger.info(String.format("Publishing event for quote request (category = %s, email = %s)", quote.getCategory(), quote.getEmail()));
		 // Send a message
		jmsTemplate.convertAndSend(JmsConfiguration.QUOTES_DESTINATION_NAME, quote);
	}
}
