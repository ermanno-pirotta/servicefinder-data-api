package servicefinder.data.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Business implements Resource{
	@Id
	private String id;

	@Field
	private String fiscalCode;
	
	@Field
	private String name;
	
	@Field
	private String surname;
	
	@Field
	private String phone;
	
	@Field
	private String email;
	
	@Field	
	@JsonIgnore
	private String password;

	@Field
	private String address;
	
	@Field
	private Long latitude;
	
	@Field
	private Long longitude;
	
	private Integer travelRangeInKm;

	@Field
	private Category category;
	
	@Field
	private List<String> providedServices;

	public Business(){
		
	}
	
	public Business(String fiscalCode) {
		this.fiscalCode = fiscalCode;
		this.id = Business.buildIdFromFiscalCode(fiscalCode);
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getLatitude() {
		return latitude;
	}

	public void setLatitude(Long latitude) {
		this.latitude = latitude;
	}

	public Long getLongitude() {
		return longitude;
	}

	public void setLongitude(Long longitude) {
		this.longitude = longitude;
	}

	public Integer getTravelRangeInKm() {
		return travelRangeInKm;
	}

	public void setTravelRangeInKm(Integer travelRangeInKm) {
		this.travelRangeInKm = travelRangeInKm;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getProvidedServices() {
		return providedServices;
	}

	public void setProvidedServices(List<String> providedServices) {
		this.providedServices = providedServices;
	}

	public String getId() {
		return id;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	public static String buildIdFromFiscalCode(String fiscalCode){
		if(fiscalCode == null || fiscalCode.isEmpty()){
			throw new IllegalArgumentException("name must not be null or empty");
		}
		return "_business_" + StringUtils.trimAllWhitespace(fiscalCode).toLowerCase();
	}
}
