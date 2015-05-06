package servicefinder.data.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Category {
	@JsonIgnore
	@Id
	private String id;

	@Field
	private String name;
	@Field
	private List<String> services;

	public Category(){
		
	}
	
	public Category(String name, List<String> services){
		this.id = buildIdFromName(name);
		this.name = name;
		this.services = services;
	}
	
	public String getId() {
        return id;
    }

	public String getName() {
		return name;
	}

	public List<String> getServices() {
		return services;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public void setServices(List<String> services) {
		this.services = services;
	}
	
	public static String buildIdFromName(String name){
		if(name == null || name.isEmpty()){
			throw new IllegalArgumentException("name must not be null or empty");
		}
		return "_category_" + StringUtils.trimAllWhitespace(name).toLowerCase();
	}
}
