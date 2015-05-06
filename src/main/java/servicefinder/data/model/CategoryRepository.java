package servicefinder.data.model;

import org.springframework.data.repository.CrudRepository;

import com.couchbase.client.protocol.views.Query;

public interface CategoryRepository extends CrudRepository<Category, String> {
	
}
