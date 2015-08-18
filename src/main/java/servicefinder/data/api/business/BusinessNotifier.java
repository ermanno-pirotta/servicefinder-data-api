package servicefinder.data.api.business;

import geonames.importer.postalcode.PostalCode;
import geonames.importer.postalcode.PostalCodeFinder;
import net.spy.memcached.compat.log.Logger;
import net.spy.memcached.compat.log.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import servicefinder.data.api.configuration.JmsConfiguration;
import servicefinder.data.api.quote.QuoteRequest;

import com.javadocmd.simplelatlng.LatLng;

@Component
public class BusinessNotifier {
	private final Logger logger = LoggerFactory.getLogger(BusinessNotifier.class);

	@Autowired
	PostalCodeFinder finder;
	
	@JmsListener(destination = JmsConfiguration.QUOTES_DESTINATION_NAME, containerFactory = "jmsContainerFactory")
	public void notifyBusinessesAboutQuote(QuoteRequest request){		
		logger.info(String.format("Received quote in category %s for user with email %s..", request.getCategoryName(), request.getEmail()));
		
		//1 search for business that should receive a notification, based on service offering and location
		
		
		//2 if a business qualifies for the quote, update it with the quote id
		//3 send an email to the businesses
	}
	
	private LatLng findCoordinatesOfTargetCustomer(String placeName, String postalCode){
		PostalCode requestPostalCode = finder.findByPlaceNameAndPostalCode(placeName, postalCode);
		return new LatLng(requestPostalCode.getLatitude(), requestPostalCode.getLongitude());
	}
}
