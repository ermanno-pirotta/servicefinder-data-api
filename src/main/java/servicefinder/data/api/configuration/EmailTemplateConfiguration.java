package servicefinder.data.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class EmailTemplateConfiguration {
	/**
	 * THYMELEAF: View Resolver - implementation of Spring's ViewResolver interface.
	 */
	@Bean
	public ViewResolver viewResolver() {
	    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	    viewResolver.setTemplateEngine(templateEngine());
	    return viewResolver;
	}
	 
	/**
	 * THYMELEAF: Template Engine (Spring4-specific version).
	 */
	@Bean
	public SpringTemplateEngine templateEngine() {
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.addTemplateResolver(emailTemplateResolver());
	    //templateEngine.addTemplateResolver(webTemplateResolver());
	    return templateEngine;
	}
	 
	/**
	 * THYMELEAF: Template Resolver for email templates.
	 */
	private TemplateResolver emailTemplateResolver() {
	    TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setPrefix("mail/");
	    templateResolver.setTemplateMode("HTML5");
	    templateResolver.setOrder(1);
	    return templateResolver;
	}
	 
	/**
	 * THYMELEAF: Template Resolver for webapp pages.
	 
	private TemplateResolver webTemplateResolver() {
	    TemplateResolver templateResolver = new ServletContextTemplateResolver();
	    templateResolver.setPrefix("/WEB-INF/templates/");
	    templateResolver.setTemplateMode("HTML5");
	    templateResolver.setOrder(2);
	    return templateResolver;
	}*/
}
