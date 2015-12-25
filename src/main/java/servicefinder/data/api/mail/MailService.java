package servicefinder.data.api.mail;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	public void send(String to, String subject, String body) throws MessagingException{
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(body,true);
		
		mailSender.send(mimeMessage);
	}
}
