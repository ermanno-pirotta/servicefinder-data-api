package servicefinder.data.api.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import servicefinder.data.api.business.Business;
import servicefinder.data.api.quote.QuoteRequest;

//check https://github.com/thymeleaf/thymeleafexamples-springmail/blob/2.1-master/src/main/java/thymeleafexamples/springmail/service/EmailService.java
//for further examples
@Component
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
    @Autowired 
    private TemplateEngine templateEngine;
    
    @Value("${site.frontend.url}")
    private String frontendUrl;
	
    /* 
     * Send HTML mail (simple) 
     */
	public void sendNotificationToBusiness(Business business, QuoteRequest request) throws MessagingException{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
		message.setTo(business.getEmail());
		message.setSubject("test");
		
		// Prepare the evaluation context
        final Context ctx = new Context();
        ctx.setVariable("subject", "test");
        ctx.setVariable("name", business.getName());
        ctx.setVariable("service",request.getRequestedServices().get(0));
        ctx.setVariable("city",request.getPlaceName());
        ctx.setVariable("postalCode",request.getPostalCode());
        ctx.setVariable("unlockLink",createUnlockLink(business,request));

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process("email-notification-business.html", ctx);
        message.setText(htmlContent, true);
        
		mailSender.send(mimeMessage);
	}
	
	private String createUnlockLink(Business business, QuoteRequest request){
		StringBuilder linkBuilder = new StringBuilder(frontendUrl);
		linkBuilder.append("/business/showquotedetails/");
		linkBuilder.append(business.getFiscalCode());
		linkBuilder.append("/");
		linkBuilder.append(String.valueOf(request.getCreationTimestamp().getTime()));
		return linkBuilder.toString();
	}
}
