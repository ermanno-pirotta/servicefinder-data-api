package servicefinder.data.api;

import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

import servicefinder.data.api.builders.BusinessTestBuilder;
import servicefinder.data.model.Business;
import servicefinder.data.model.BusinessRepository;

public class BusinessControllerTests extends ControllerTest {

	@Autowired
	BusinessRepository businessRepository;

	@Test
	public void shouldFindByFiscalCode() throws Exception {
		final String fiscalCode = "fiscalCode";
		Business business = BusinessTestBuilder.buildTestBusiness(fiscalCode);
		business = businessRepository.save(business);

		this.mockMvc.perform(get("/businesses/" + Business.buildIdFromFiscalCode(fiscalCode))
							.contentType(contentType))
					.andExpect(content().contentType(contentType))
					.andExpect(status().isOk())
					.andExpect(content().json(jsonOf(business)));

		businessRepository.delete(business);
	}

	@Test
	public void shouldGiveNoContentWhenMissingBusiness() throws Exception{
		this.mockMvc.perform(get("/businesses/NotExisting")
							.contentType(contentType))
					.andExpect(status().isNoContent());	  	 
	}

	@Test
	public void shouldAddANewBusiness() throws Exception{
		Business business = BusinessTestBuilder.buildTestBusiness();
		  String businessJson = this.jsonOf(business);

		  this.mockMvc.perform(post("/businesses")
				              .contentType(contentType)
				              .content(businessJson))
					  .andExpect(status().isCreated())
	              .andExpect(content().contentType(contentType))
	              .andExpect(content().json(businessJson));
		  
		  businessRepository.delete(business);
	}

	@Test
	public void shouldGiveAConflictWhenCreatingWithExistingFiscalCode()
			throws Exception {
		Business business = BusinessTestBuilder.buildTestBusiness();
		businessRepository.save(business);

		this.mockMvc.perform(post("/businesses")
							.contentType(contentType)
							.content(jsonOf(business)))
					.andExpect(status().isConflict());

		businessRepository.delete(business);
	}

	@Test
	@Ignore
	public void shouldUnsubscribeAnExistingBusiness() throws Exception {
		String fiscalCode = "test";
		Business business = BusinessTestBuilder.buildTestBusiness(fiscalCode);
		businessRepository.save(business);
		
		this.mockMvc.perform(delete("/businesses" + Business.buildIdFromFiscalCode(fiscalCode) ))
							.andExpect(status().isAccepted());
		
		Business businessOnStorage = businessRepository.findOne(Business.buildIdFromFiscalCode(fiscalCode));
		assertNull(businessOnStorage);
	}

	@Test
	public void shouldGiveAnHttpPreconditionFailedErrorWhenDeletingNotExisting() throws Exception{
		this.mockMvc.perform(delete("/businesses/" + Business.buildIdFromFiscalCode("NotExisting") ))
							.andExpect(status().isPreconditionFailed());
	}

	@Override
	protected CrudRepository<?, String> getRepository() {
		return businessRepository;
	}
}
