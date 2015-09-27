package servicefinder.data.api.business;

import static com.javadocmd.simplelatlng.LatLngTool.distance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.couchbase.client.protocol.views.ComplexKey;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;
import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.util.LengthUnit;

@Component
public class BusinessFinder {
	
	@Autowired
	BusinessRepository repository;
	
	/**
	 * 
	 * @param requiredServices
	 * @param targetLocation
	 * @return a map containing the business and the nr of required services which have been matched
	 */
	public Map<Business, Integer> findByServiceAndLocation(List<String> requiredServices, LatLng targetLocation){
		Map<Business, Integer> businessesWithRequiredServices = findByProvidedServices(requiredServices);
		
		return orderByValue(filterByLocation(businessesWithRequiredServices, targetLocation));
	}
	
	/**
	 * 
	 * @param requiredServices
	 * @return all the businesses that fullfil at least one of the required services
	 */
	public Map<Business, Integer> findByProvidedServices(List<String> requiredServices){
		if(requiredServices == null || requiredServices.isEmpty() ){
			return new HashMap<Business, Integer>();
		}
		List<Business> businessesFromStorage = findByProvidedServices(requiredServices.toArray(new String[0]));
		return groupByBusiness(businessesFromStorage);
	}
	
	private List<Business> findByProvidedServices(String[] requiredServices){
		Query query = new Query();
		if(requiredServices.length == 1){
			query.setKey(ComplexKey.of(requiredServices));
		}
		else{
			query.setKeys(ComplexKey.of(requiredServices));
		}

		query.setStale(Stale.FALSE);
		
		return repository.findByProvidedServices(query);
	}
	
	private Map<Business, Integer> groupByBusiness(List<Business> resultToGroup){
		Map<Business, Integer> resultGroupedByBusiness = new HashMap<Business, Integer>();
		
		for(Business business: resultToGroup){
			if(resultGroupedByBusiness.containsKey(business)){
				resultGroupedByBusiness.put(business,resultGroupedByBusiness.get(business) + 1);
			}
			else{
				resultGroupedByBusiness.put(business, 1);
			}
		}
		
		return resultGroupedByBusiness;
	}
	
	private Map<Business, Integer> orderByValue(Map<Business, Integer> mapToOrder){
		final Ordering<Business> reverseValuesAndNaturalKeysOrdering =
			    Ordering.natural().reverse().nullsLast().onResultOf(Functions.forMap(mapToOrder, null)).compound(Ordering.natural());// natural for values
			        //.compound(Ordering.natural()); // secondary - natural ordering of keys
		return ImmutableSortedMap.copyOf(mapToOrder, reverseValuesAndNaturalKeysOrdering);
	}
	
	/**
	 * 
	 * @param businessesToFilter the list of businesses to filter
	 * @param targetCoordinates the coordinates to compare with
	 * @return a list of businesses that fit the targetLocation, based on distance and willingness to travel
	 */
	private Map<Business, Integer> filterByLocation(Map<Business, Integer> businessesToFilter, LatLng targetCoordinates){
		Map<Business, Integer> businessByServiceAndLocation = new HashMap<Business, Integer>();
		for(Business business : businessesToFilter.keySet()){
			LatLng businessCoordinates = new LatLng(business.getLatitude(), business.getLongitude());
			if(distance(businessCoordinates,targetCoordinates, LengthUnit.KILOMETER) <= business.getTravelRangeInKm()){
				businessByServiceAndLocation.put(business, businessesToFilter.get(business));
			}
		}
		
		return businessByServiceAndLocation;
	}
}
