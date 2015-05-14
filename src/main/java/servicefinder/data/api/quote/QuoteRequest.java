package servicefinder.data.api.quote;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

import servicefinder.data.api.category.Category;
import servicefinder.data.api.common.Resource;

@Document
public class QuoteRequest implements Resource, Serializable{

	private static final long serialVersionUID = -6097635701783502292L;
	
	@Id
	private String id;

	@Field
	private final Date creationTimestamp;

	private String name;
	private String surname;
	private String email;
	private String phone;
	private String zipCode;
	
	private Category category;
	private List<String> requestedServices;
	private String description;
	private boolean isActive;

	public QuoteRequest() {
		this.creationTimestamp = new Date();
		this.id = buildIdFromTimestamp(this.creationTimestamp);
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getRequestedServices() {
		return requestedServices;
	}

	public void setRequestedServices(List<String> requestedServices) {
		this.requestedServices = requestedServices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public static String buildIdFromTimestamp(Date creationTimestamp){
		return "#" + String.valueOf(creationTimestamp.getTime());
	}

}
