package servicefinder.data.api;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import geonames.importer.postalcode.PostalCode;
import geonames.importer.postalcode.PostalCodeFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import servicefinder.data.api.builders.QuoteRequestTestBuilder;
import servicefinder.data.api.business.Business;
import servicefinder.data.api.business.BusinessBuilder;
import servicefinder.data.api.business.BusinessFinder;
import servicefinder.data.api.business.BusinessRepository;
import servicefinder.data.api.quote.QuoteRequest;

import com.google.common.collect.Lists;
import com.javadocmd.simplelatlng.LatLng;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServicefinderDataApiApplication.class)
@WebAppConfiguration
@TestPropertySource("/application-test.properties")
public class BusinessFinderTests {

	@Autowired
	BusinessFinder finder;
	
	@Autowired
	BusinessRepository businessRepo;
 	
	@Autowired
	PostalCodeFinder postalCodeFinder;
	
	private List<String> requiredServices;
	
	private static LatLng PALOSCO_COORDINATES = new LatLng(45.5881351, 9.8362088);
	private static LatLng CALCINATE_COORDINATES = new LatLng(45.6178184, 9.806089);
	private static LatLng MILANO_COORDINATES = new LatLng(45.4627338, 9.1777322);
	private static String TEST_CATEGORY = "test_category";
	
	@Before
	public void setup(){
		requiredServices = new ArrayList<String>();
		requiredServices.add("service-a");
	}
	
	@Test
	public void shouldFindABusinessProvidingAtLeastOneService(){
		Business business =buildAndSaveBusiness(Lists.newArrayList("service-a","service-b"));

		Map<Business,Integer> businessesFromFinder = finder.findByProvidedServices(requiredServices);

		assertThat(businessesFromFinder, is(notNullValue()));
		assertThat(businessesFromFinder,hasKey(business));
		
		businessRepo.delete(business);
	}
	
	@Test
	public void shouldNotReturnIfNotHaveRequestedService(){
		Business business =buildAndSaveBusiness(Lists.newArrayList("service-b"));

		Map<Business,Integer> businessesFromFinder = finder.findByProvidedServices(requiredServices);

		assertThat(businessesFromFinder, is(notNullValue()));
		assertThat(businessesFromFinder,not(hasKey(business)));
		
		businessRepo.delete(business);
	}
	
	@Test
	public void shouldFindABusinessProvidingMoreServices(){
		Business business =buildAndSaveBusiness(Lists.newArrayList("service-a","service-b", "service-c"));
		requiredServices.add("service-b");
		
		Map<Business,Integer> businessesFromFinder = finder.findByProvidedServices(requiredServices);
		
		assertThat(businessesFromFinder, is(notNullValue()));
		assertThat(businessesFromFinder,hasKey(business));
		
		businessRepo.delete(business);
	}
	
	@Test
	public void shouldReturnAnEmptyMapIfNoServicesAreSearched(){
		Map<Business,Integer> businessesFromFinder = finder.findByProvidedServices(null);
		
		assertThat(businessesFromFinder, is(notNullValue()));
		assertThat(businessesFromFinder.size(), is(0));
	}
	
	@Test
	public void shouldReturnOnlyBusinessesWithServiceAndRightLocation(){
		Business businessInPalosco =buildAndSaveBusinessInPalosco();
		Business businessInMilano =buildAndSaveBusinessInMilano();
		
		QuoteRequest requestInPalosco = buildQuoteCloseToPalosco();
		PostalCode requestPostalCode = postalCodeFinder.findByPlaceNameAndPostalCode(requestInPalosco.getPlaceName(), requestInPalosco.getPostalCode());
		LatLng requestCoordinates = new LatLng(requestPostalCode.getLatitude(),requestPostalCode.getLongitude());
		Map<Business, Integer> matchingBusinesses = finder.findByServiceAndLocation(requestInPalosco.getRequestedServices(), requestCoordinates);
		
		assertThat("business in Palosco matches request for Palosco", matchingBusinesses.containsKey(businessInPalosco));
		assertThat("business in Milano does not match request for Palosco", not(matchingBusinesses.containsKey(businessInMilano)));
		
		businessRepo.delete(businessInPalosco);
		businessRepo.delete(businessInMilano);
	}
	
	@Test
	public void shouldFindBusinessesWIthinTravelRange(){
		Business businessInCalcinate = buildAndSaveBusinessNearPalosco();
		QuoteRequest requestInPalosco = buildQuoteCloseToPalosco();
		
		Map<Business, Integer> matchingBusinesses = finder.findByServiceAndLocation(requestInPalosco.getRequestedServices(), PALOSCO_COORDINATES);
		
		assertThat("business in Palosco matches request for Palosco", matchingBusinesses.containsKey(businessInCalcinate));
		
		businessRepo.delete(businessInCalcinate);
		
	}
	
	private Business buildAndSaveBusiness(List<String> services){
		Business business = new BusinessBuilder().withFiscalCode("test").withName("test").withProvidedServices(services).build();
		return businessRepo.save(business);
	}
	
	private Business buildAndSaveBusinessInPalosco(){
		Business businessInPalosco =   new BusinessBuilder()
		.withFiscalCode("palosco")
		.withName("palosco-business")
		.withProvidedServices(Lists.newArrayList("test service 1", "test service 2"))
		.withLatitude(PALOSCO_COORDINATES.getLatitude())
		.withLongitude(PALOSCO_COORDINATES.getLongitude())
		.withTravelRangeInKm(10)
		.build();
		
		return businessRepo.save(businessInPalosco);
	}
	
	private Business buildAndSaveBusinessInMilano(){
		Business businessInPalosco =   new BusinessBuilder()
		.withFiscalCode("milano")
		.withName("milano-business")
		.withProvidedServices(Lists.newArrayList("service-test"))
		.withLatitude(MILANO_COORDINATES.getLatitude())
		.withLongitude(MILANO_COORDINATES.getLongitude())
		.withTravelRangeInKm(20)
		.build();
		
		return businessRepo.save(businessInPalosco);
	}
	
	private Business buildAndSaveBusinessNearPalosco(){
		Business businessNearPalosco =   new BusinessBuilder()
		.withFiscalCode("nearPalosco")
		.withName("calcinate-business")
		.withProvidedServices(Lists.newArrayList("test service 1", "test service 2"))
		.withLatitude(CALCINATE_COORDINATES.getLatitude())
		.withLongitude(CALCINATE_COORDINATES.getLongitude())
		.withTravelRangeInKm(20)
		.build();
		
		return businessRepo.save(businessNearPalosco);
	}
	
	private QuoteRequest buildQuoteCloseToPalosco(){
		QuoteRequest quote = new QuoteRequestTestBuilder()
							.withPlaceName("palosco")
							.withPostalCode("24050")
							.withCategoryName(TEST_CATEGORY)
							.build();
		return quote;
	}
	
}
