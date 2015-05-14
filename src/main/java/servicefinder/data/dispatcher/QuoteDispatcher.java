package servicefinder.data.dispatcher;

import net.spy.memcached.compat.log.LoggerFactory;

import net.spy.memcached.compat.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import servicefinder.data.configuration.JmsConfiguration;
import servicefinder.data.model.QuoteRequest;
import servicefinder.data.model.QuoteRequestRepository;

@Component
public class QuoteDispatcher {
	private final Logger logger = LoggerFactory.getLogger(QuoteDispatcher.class);
	
	@Autowired
	BusinessNotifier notifier;
	
	@Autowired
	QuoteRequestRepository quoteRepository;
	
	@JmsListener(destination = JmsConfiguration.QUOTES_DESTINATION_NAME, containerFactory = "jmsContainerFactory")
	public void processQuoteRequest(QuoteRequest request){		
		logger.info(String.format("Dispatching for quote in category %s for user with email %s..", request.getCategory(), request.getEmail()));
		
		
	}
}
