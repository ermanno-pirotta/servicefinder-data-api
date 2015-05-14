package servicefinder.data.api.builders;

import servicefinder.data.api.business.Business;

public class BusinessTestBuilder {
	public final static String DEFAULT_FISCAL_CODE = "test";
	
	public static Business buildTestBusiness() {
		return buildTestBusiness(DEFAULT_FISCAL_CODE);
	}
	
	public static Business buildTestBusiness(String fiscalCode) {
		Business business = new Business(fiscalCode);
		return business;
	}
}
