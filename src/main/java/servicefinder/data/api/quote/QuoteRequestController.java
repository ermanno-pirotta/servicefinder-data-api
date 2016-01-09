package servicefinder.data.api.quote;

import java.util.Date;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import servicefinder.data.api.business.Business;
import servicefinder.data.api.business.BusinessRepository;
import servicefinder.data.api.configuration.JmsConfiguration;
import servicefinder.data.api.http.HttpHeaderBuilder;

import com.fasterxml.jackson.annotation.JsonView;
import com.paymill.context.PaymillContext;
import com.paymill.models.Client;
import com.paymill.models.Payment;
import com.paymill.models.Transaction;
import com.paymill.services.ClientService;
import com.paymill.services.PaymentService;
import com.paymill.services.TransactionService;

@RestController
@RequestMapping("/quotes")
public class QuoteRequestController {
			
	private final Logger logger = LoggerFactory.getLogger(QuoteRequestController.class);
	
	@Autowired
	private HttpHeaderBuilder headerBuilder;
	
	@Autowired
	private QuoteRequestRepository quoteRequestRepository;
	
	@Autowired
	private BusinessRepository businessRepository;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private PaymillContext paymentContext;

	@JsonView(View.Summary.class)
	@RequestMapping(method = RequestMethod.GET, value="/{timestamp}")	
	ResponseEntity<?> get(@PathVariable String timestamp){
		long creationTimestamp = new Long(timestamp);
		QuoteRequest quote = quoteRequestRepository.findOne(QuoteRequest.buildIdFromTimestamp(new Date(creationTimestamp)));	
		
		if(quote != null){
			return new ResponseEntity<>(quote, headerBuilder.buildHttpHeadersForResource(quote), HttpStatus.OK);
		}
		else{ 
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{timestamp}/pay")	
	ResponseEntity<?> payAndReturnQuote(@PathVariable String timestamp, @RequestParam("token") String token, @RequestParam("businessId") String businessId){
		Business business = businessRepository.findOne(Business.buildIdFromFiscalCode(businessId));
		long creationTimestamp = new Long(timestamp);
		QuoteRequest quote = quoteRequestRepository.findOne(QuoteRequest.buildIdFromTimestamp(new Date(creationTimestamp)));	
		
		if(business == null){
			return new ResponseEntity<>("BUSINESS_NOT_FOUND", headerBuilder.buildHttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		if(quote == null){
			return new ResponseEntity<>("QUOTE_NOT_FOUND", headerBuilder.buildHttpHeaders(), HttpStatus.BAD_REQUEST);
		}
		
		try{
			ClientService clientService = paymentContext.getClientService();
			Client client = clientService.createWithEmailAndDescription(
					business.getEmail(),
					business.getFiscalCode()
					);
			
			PaymentService paymentService = paymentContext.getPaymentService();
			Payment payment = paymentService.createWithTokenAndClient(
					token,
					client.getId()
					);
			
			TransactionService transactionService = paymentContext.getTransactionService();
			Transaction transaction = transactionService.createWithPaymentAndClient(
					payment.getId(),
					client.getId(),
					1000, //amount in cents
					"EUR"
					);
			
		}
		catch(Throwable ex){
			ex.printStackTrace();
		}
		
		
		
		return new ResponseEntity<>(quote, headerBuilder.buildHttpHeadersForResource(quote), HttpStatus.OK);
		
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
