package servicefinder.data.api.business;

import java.util.List;

import org.springframework.data.couchbase.core.view.View;
import org.springframework.data.repository.CrudRepository;

import com.couchbase.client.protocol.views.Query;

public interface BusinessRepository extends CrudRepository<Business, String> {
	/**
	 * 
	 * @param query containing the list of services to match
	 * @return a map containing a business that provides at least one service, and the number of services matching the request
	 */
	@View(designDocument = "business", viewName = "byProvidedServices")
	List<Business> findByProvidedServices(Query query);	

}