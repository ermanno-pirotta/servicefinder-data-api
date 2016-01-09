package servicefinder.data.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paymill.context.PaymillContext;

@Configuration
public class PaymentConfiguration {
	public static final String API_KEY = "2f6124a431b9954b29861059c257d373";
	
	@Bean
	public PaymillContext paymentContext(){
		return new PaymillContext(API_KEY);
	}

}
