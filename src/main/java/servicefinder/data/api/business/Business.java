package servicefinder.data.api.business;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;
import org.springframework.util.StringUtils;

import servicefinder.data.api.common.Resource;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document
public class Business implements Resource, Comparable<Business>{
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
	private Double latitude;
	
	@Field
	private Double longitude;
	
	private Integer travelRangeInKm;

	@Field
	private String categoryName;
	
	@Field
	private List<String> providedServices;
	
	@Field
	private Set<String> pendingQuotes = new HashSet<String>();

	public Business(){
		
	}
	
	@GeneratePojoBuilder
	public Business(String fiscalCode) {
		setFiscalCode(fiscalCode);
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

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getTravelRangeInKm() {
		return travelRangeInKm;
	}

	public void setTravelRangeInKm(Integer travelRangeInKm) {
		this.travelRangeInKm = travelRangeInKm;
	}


	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<String> getProvidedServices() {
		return providedServices;
	}

	public void setProvidedServices(List<String> providedServices) {
		this.providedServices = providedServices;
	}

	public Set<String> getPendingQuotes() {
		return pendingQuotes;
	}

	public void addQuote(String quote){
		this.pendingQuotes.add(quote);
	}
	
	public String getId() {
		return id;
	}

	public String getFiscalCode() {
		return fiscalCode;
	}

	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
		//TODO: this is not a nice design, since the setFiscalCode does more than what it is supposed to do
		this.id = buildIdFromFiscalCode(fiscalCode);
		
	}
	
	public static String buildIdFromFiscalCode(String fiscalCode){
		if(fiscalCode == null || fiscalCode.isEmpty()){
			throw new IllegalArgumentException("fiscal must not be null or empty");
		}
		return "_business_" + StringUtils.trimAllWhitespace(fiscalCode).toLowerCase();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((categoryName == null) ? 0 : categoryName.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((fiscalCode == null) ? 0 : fiscalCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result
				+ ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((pendingQuotes == null) ? 0 : pendingQuotes.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime
				* result
				+ ((providedServices == null) ? 0 : providedServices.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result
				+ ((travelRangeInKm == null) ? 0 : travelRangeInKm.hashCode());
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
		Business other = (Business) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (fiscalCode == null) {
			if (other.fiscalCode != null)
				return false;
		} else if (!fiscalCode.equals(other.fiscalCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pendingQuotes == null) {
			if (other.pendingQuotes != null)
				return false;
		} else if (!pendingQuotes.equals(other.pendingQuotes))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (providedServices == null) {
			if (other.providedServices != null)
				return false;
		} else if (!providedServices.equals(other.providedServices))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (travelRangeInKm == null) {
			if (other.travelRangeInKm != null)
				return false;
		} else if (!travelRangeInKm.equals(other.travelRangeInKm))
			return false;
		return true;
	}

	@Override
	public int compareTo(Business o) {
		//keep alphabetical order based on company name
		return this.name.compareTo(o.getName());
	}

}
