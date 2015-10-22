package servicefinder.data.api.category;

import java.io.Serializable;
import java.util.List;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.util.StringUtils;

import servicefinder.data.api.http.Resource;

@Document
public class Category implements Serializable, Resource{

	private static final long serialVersionUID = 5672760542691584764L;

	@Id
	private String id;

	@Field
	private String name;
	
	@Field
	private String description;
	
	@Field
	private List<String> services;

	public Category(){
		
	}
	
	@GeneratePojoBuilder
	public Category(String name){
		this.name = name;
		this.id = buildIdFromName(name);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	@Override
	public String toString(){
		return this.name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((services == null) ? 0 : services.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (services == null) {
			if (other.services != null)
				return false;
		} else if (!services.equals(other.services))
			return false;
		return true;
	}
	
	
}
