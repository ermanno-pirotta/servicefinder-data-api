package servicefinder.data.api.business;

import geonames.importer.postalcode.PostalCode;
import geonames.importer.postalcode.PostalCodeFinder;

import java.util.Map;

import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import servicefinder.data.api.configuration.JmsConfiguration;
import servicefinder.data.api.quote.QuoteRequest;

import com.javadocmd.simplelatlng.LatLng;

@Component
public class BusinessService {
	private final Logger logger = LoggerFactory.getLogger(BusinessService.class);

	@Autowired
	PostalCodeFinder finder;
	
	@Autowired 
	BusinessFinder businessFinder;
	
	@Autowired
	BusinessRepository repository;
	
	@JmsListener(destination = JmsConfiguration.QUOTES_DESTINATION_NAME, containerFactory = "jmsContainerFactory")
	public void notifyBusinessesAboutQuote(QuoteRequest request){		
		logger.info(String.format("Received quote in category %s for user with email %s..", request.getCategoryName(), request.getEmail()));
		
		//1 search for business that should receive a notification, based on service offering and location
		LatLng customerCoordinates = findCoordinatesOfTargetCustomer(request.getPlaceName(),request.getPostalCode());
		
		Map<Business, Integer> matchingBusinesses = businessFinder.findByServiceAndLocation(request.getRequestedServices(), customerCoordinates);
		
		//2 if a business qualifies for the quote, update it with the quote id
		updateBusinesses(matchingBusinesses, request.getId());
		
		//3 send an email to the businesses
	}
	
	private LatLng findCoordinatesOfTargetCustomer(String placeName, String postalCode){
		PostalCode requestPostalCode = finder.findByPlaceNameAndPostalCode(placeName, postalCode);
		return new LatLng(requestPostalCode.getLatitude(), requestPostalCode.getLongitude());
	}
	
	public void updateBusinesses(Map<Business, Integer> businesses, String quote){
		for(Business business: businesses.keySet()){
			updateBusinessWithQuoteId(business,quote);
		}
	}
	
	private void updateBusinessWithQuoteId(Business business, String quote){
		business.addQuote(quote);
		repository.save(business);
	}
}
